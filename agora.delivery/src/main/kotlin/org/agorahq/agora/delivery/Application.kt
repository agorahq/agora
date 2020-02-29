@file:Suppress("UNCHECKED_CAST", "EXPERIMENTAL_API_USAGE", "unused")

package org.agorahq.agora.delivery

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.features.origin
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.netty.EngineMain
import io.ktor.sessions.*
import io.ktor.util.hex
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.comment.operations.DeleteComment
import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.comment.operations.RenderCommentForm
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.data.UserMetadata
import org.agorahq.agora.core.api.document.ResourceURL
import org.agorahq.agora.core.api.extensions.AnyContentResourceOperation
import org.agorahq.agora.core.api.extensions.AnyPageRenderer
import org.agorahq.agora.core.api.extensions.AnyResourceListRenderer
import org.agorahq.agora.core.api.extensions.whenHasOperation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.services.ModuleRegistry
import org.agorahq.agora.core.api.services.impl.InMemoryDocumentElementQueryService
import org.agorahq.agora.core.api.services.impl.InMemoryDocumentQueryService
import org.agorahq.agora.core.api.services.impl.InMemoryStorageService
import org.agorahq.agora.core.api.shared.templates.HOMEPAGE
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
import org.agorahq.agora.core.services.DefaultModuleRegistry
import org.agorahq.agora.delivery.DefaultRoles.ADMIN
import org.agorahq.agora.delivery.DefaultRoles.ATTENDEE
import org.agorahq.agora.delivery.extensions.create
import org.agorahq.agora.delivery.extensions.mapTo
import org.agorahq.agora.delivery.extensions.toTimestamp
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.agorahq.agora.post.operations.CreatePost
import org.agorahq.agora.post.operations.DeletePost
import org.agorahq.agora.post.operations.ListPosts
import org.agorahq.agora.post.operations.RenderPost
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.logging.api.LoggerFactory
import java.time.LocalDate
import java.util.concurrent.ConcurrentHashMap

val POST_A_ID = UUID.randomUUID()
val POST_B_ID = UUID.randomUUID()

val POST_A = Post(
        id = POST_A_ID,
        title = "Agora is launching soon",
        tags = setOf("agora", "post"),
        createdAt = LocalDate.of(2019, 12, 28).toTimestamp(),
        shortDescription = "Agora is planned to launch in early Q2.",
        content = """
            After half a year of active development Agora is planned to launch in closed beta.
            
            A few groups will be chosen to participate in it and the devs will use the feedback to
            make the product even better.
        """.trimIndent())

val POST_B = Post(
        id = POST_B_ID,
        title = "Agora is using Ktor",
        tags = setOf("agora", "ktor"),
        createdAt = LocalDate.of(2019, 12, 29).toTimestamp(),
        shortDescription = "Ktor have been chosen to be used as the server technology for Agora",
        content = """
            After careful consideration *Ktor* have been chosen to be used as the server technology for Agora.
            
            We've checked a lot of other tools like *Spring* and *vert.x* but in the end this looked the most promising.
        """.trimIndent())

val COMMENT_A_0 = Comment(
        content = "**Wow**, that's great!",
        author = "Jack",
        parentId = POST_A_ID)

val COMMENT_B_0 = Comment(
        content = "I *can't wait*!",
        author = "Jenna",
        parentId = POST_B_ID)

val COMMENT_B_1 = Comment(
        content = "I've been using *Ktor* for a while now and I'm happy to report that this was a great choice!",
        author = "Frank",
        parentId = POST_B_ID)

val POSTS = ConcurrentHashMap<UUID, Post>().apply {
    put(POST_A_ID, POST_A)
    put(POST_B_ID, POST_B)
}
val COMMENTS = ConcurrentHashMap<UUID, Comment>().apply {
    put(COMMENT_A_0.id, COMMENT_A_0)
    put(COMMENT_B_0.id, COMMENT_B_0)
    put(COMMENT_B_1.id, COMMENT_B_1)
}

val CLIENT_ID = System.getenv("AGORA_OAUTH_CLIENT_ID") ?: error("AGORA_OAUTH_CLIENT_ID env variable is missing")
val CLIENT_SECRET = System.getenv("AGORA_OAUTH_CLIENT_SECRET")
        ?: error("AGORA_OAUTH_CLIENT_SECRET env variable is missing")

val SITE = DefaultSiteMetadata(
        title = "Agora",
        email = "info@agorahq.org",
        description = "Agora Site",
        baseUrl = "/",
        moduleRegistry = DefaultModuleRegistry())

private val logger = LoggerFactory.getLogger("Application")

private fun ApplicationCall.redirectUrl(path: String): String {
    val defaultPort = if (request.origin.scheme == "http") 80 else 443
    val hostPort = request.host() + request.port().let { port -> if (port == defaultPort) "" else ":$port" }
    val protocol = request.origin.scheme
    return "$protocol://$hostPort$path"
}

private suspend fun ApplicationCall.tryRedirectToReferrer() {
    request.headers["Referer"]?.let { referer ->
        respondRedirect(referer)
    } ?: respondRedirect(SITE.baseUrl)
}

class AgoraSession(
        val userId: String,
        val userEmail: String,
        val userNick: String,
        val role: String
) {

    fun toUser(): User = UserMetadata.create(
            email = userEmail,
            username = userNick,
            roles = setOf(DefaultRoles.valueOf(role).role)).toUser()
}

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {

    val googleOauthProvider = OAuthServerSettings.OAuth2ServerSettings(
            name = "google",
            authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
            accessTokenUrl = "https://www.googleapis.com/oauth2/v3/token",
            requestMethod = HttpMethod.Post,

            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            defaultScopes = listOf("profile", "email")  // no email, but gives full name, picture, and id
    )


    install(Sessions) {
        cookie<AgoraSession>("oauthSampleSessionId") {
            val secretSignKey = hex("000102030405060708090a0b0c0d0e0f")
            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
        }
    }

    install(Authentication) {
        oauth("google-oauth") {
            client = HttpClient(Apache)
            providerLookup = { googleOauthProvider }
            urlProvider = { redirectUrl("/login") }
        }
    }

    routing {

        get("/logout") {
            call.sessions.clear<AgoraSession>()
            call.tryRedirectToReferrer()
        }

        authenticate("google-oauth") {
            route("/login") {
                handle {
                    val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                            ?: error("No principal")

                    val json = HttpClient(Apache).get<String>("https://www.googleapis.com/userinfo/v2/me") {
                        header("Authorization", "Bearer ${principal.accessToken}")
                    }

                    val data = ObjectMapper().readValue<Map<String, Any?>>(json)
                    val email = data["email"] as String?

                    email?.let {
                        log.info("User data is: $data.")
                        call.sessions.set(AgoraSession(
                                userId = UUID.randomUUID().toString(),
                                userEmail = it,
                                userNick = data["given_name"]?.toString() ?: "anonymous",
                                role = if (it == "arold.adam@gmail.com") ADMIN.name else ATTENDEE.name))
                    } ?: error("An email address is mandatory for a User to log in.")
                    call.tryRedirectToReferrer()
                }
            }
        }
        registerModules(createModules(SITE))
    }
}

private fun createModules(site: SiteMetadata): ModuleRegistry {
    val moduleRegistry = site.moduleRegistry
    val commentQueryService = InMemoryDocumentElementQueryService(COMMENTS)
    val postQueryService = InMemoryDocumentQueryService(POSTS)
    val commentStorage = InMemoryStorageService(COMMENTS)
    val postStorage = InMemoryStorageService(POSTS)

    val listPosts = ListPosts(postQueryService)
    val renderPost = RenderPost(postQueryService)
    val createPost = CreatePost(postStorage)
    val deletePost = DeletePost(postStorage)

    val listComments = ListComments(commentQueryService)
    val renderCommentForm = RenderCommentForm()
    val createComment = CreateComment(commentStorage)
    val deleteComment = DeleteComment(commentStorage)

    val postModule = PostModule(listOf(listPosts, renderPost, listComments))
    val commentModule = CommentModule(listOf(renderCommentForm, createComment))

    moduleRegistry.register(postModule)
    moduleRegistry.register(commentModule)

    return moduleRegistry
}

fun ApplicationCall.toOperationContext(): OperationContext {
    val session = sessions.get<AgoraSession>()
    val user = session?.toUser() ?: UserMetadata.ANONYMOUS
    return OperationContext.create(SITE, user)
}

private fun Routing.registerModules(moduleRegistry: ModuleRegistry) {
    get(SITE.baseUrl) {
        call.respondText(HOMEPAGE.render(call.toOperationContext()), ContentType.Text.Html)
    }
    logger.info("Registering modules...")
    moduleRegistry.modules.forEach { module ->

        module.whenHasOperation<AnyResourceListRenderer> { documentListing ->
            logger.info("Registering module ${documentListing::class.simpleName} at ${documentListing.route}")
            get(documentListing.route) {
                call.respondText(
                        text = with(documentListing) { call.toOperationContext().execute() },
                        contentType = ContentType.Text.Html)
            }
        }

        module.whenHasOperation<AnyPageRenderer> { renderer ->
            logger.info("Registering module ${renderer::class.simpleName} at ${renderer.route}")
            get(renderer.route) {
                val url = ResourceURL.create(
                        klass = renderer.urlType,
                        parameters = call.parameters)
                val ctx = call.toOperationContext().toPageContext(url)
                call.respondText(
                        text = with(renderer) { ctx.execute() },
                        contentType = ContentType.Text.Html)
            }
        }

        module.whenHasOperation<AnyContentResourceOperation> { op ->
            logger.info("Registering module ${op::class.simpleName} at ${op.route}")
            post(op.route) {
                with(op) {
                    call.toOperationContext()
                            .toResourceContext(call.receiveParameters().mapTo(op.resourceClass))
                            .execute()
                }
                call.tryRedirectToReferrer()
            }
        }

    }
}