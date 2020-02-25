@file:Suppress("UNCHECKED_CAST", "EXPERIMENTAL_API_USAGE")

package org.agorahq.agora.delivery

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.auth.Authentication
import io.ktor.auth.OAuthAccessTokenResponse
import io.ktor.auth.OAuthServerSettings
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.auth.oauth
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
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.clear
import io.ktor.sessions.cookie
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.hex
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.comment.operations.CommentCreator
import org.agorahq.agora.comment.operations.CommentLister
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.User
import org.agorahq.agora.core.domain.document.PageURL
import org.agorahq.agora.core.extensions.AnyPageContentCreator
import org.agorahq.agora.core.extensions.AnyPageRenderer
import org.agorahq.agora.core.extensions.whenHasOperation
import org.agorahq.agora.core.module.operations.PageLister
import org.agorahq.agora.core.services.DefaultModuleRegistry
import org.agorahq.agora.core.services.ModuleRegistry
import org.agorahq.agora.core.services.impl.InMemoryDocumentElementQueryService
import org.agorahq.agora.core.services.impl.InMemoryDocumentQueryService
import org.agorahq.agora.core.services.impl.InMemoryStorageService
import org.agorahq.agora.core.shared.templates.HOMEPAGE
import org.agorahq.agora.delivery.extensions.create
import org.agorahq.agora.delivery.extensions.mapTo
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.agorahq.agora.post.operations.PostLister
import org.agorahq.agora.post.operations.PostRenderer
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.logging.api.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

val POST_A_ID = UUID.randomUUID()
val POST_B_ID = UUID.randomUUID()

val POST_A = Post(
        id = POST_A_ID,
        title = "Agora is launching soon",
        date = "2019-12-28",
        shortDescription = "Agora is planned to launch in early Q2.",
        rawContent = """
            After half a year of active development Agora is planned to launch in closed beta.
            
            A few groups will be chosen to participate in it and the devs will use the feedback to
            make the product even better.
        """.trimIndent())

val POST_B = Post(
        id = POST_B_ID,
        title = "Agora is using Ktor",
        date = "2019-12-29",
        shortDescription = "Ktor have been chosen to be used as the server technology for Agora",
        rawContent = """
            After careful consideration *Ktor* have been chosen to be used as the server technology for Agora.
            
            We've checked a lot of other tools like *Spring* and *vert.x* but in the end this looked the most promising.
        """.trimIndent())

val COMMENT_A_0 = Comment(
        rawContent = "**Wow**, that's great!",
        author = "Jack",
        parentId = POST_A_ID)

val COMMENT_B_0 = Comment(
        rawContent = "I *can't wait*!",
        author = "Jenna",
        parentId = POST_B_ID)

val COMMENT_B_1 = Comment(
        rawContent = "I've been using *Ktor* for a while now and I'm happy to report that this was a great choice!",
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

val SITE = Site(
        title = "Agora",
        email = "info@agorahq.org",
        description = "Agora Site",
        host = "localhost",
        port = 8080,
        baseUrl = "/",
        moduleRegistry = DefaultModuleRegistry())

private val logger = LoggerFactory.getLogger("Application")

private fun ApplicationCall.redirectUrl(path: String): String {
    val defaultPort = if (request.origin.scheme == "http") 80 else 443
    val hostPort = request.host() + request.port().let { port -> if (port == defaultPort) "" else ":$port" }
    val protocol = request.origin.scheme
    return "$protocol://$hostPort$path"
}

class AgoraSession(val userId: String,
                   val userFirstName: String,
                   val userLastName: String)

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.run() {

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
            call.respondRedirect(SITE.baseUrl)
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
                    val id = data["id"] as String?

                    if (id != null) {
                        log.info("User data is: $data.")
                        call.sessions.set(AgoraSession(
                                userId = id,
                                userFirstName = data["given_name"]?.toString() ?: "anonymous",
                                userLastName = data["family_name"]?.toString() ?: "anonymous"))
                    }
                    call.respondRedirect(SITE.baseUrl)
                }
            }
        }
        registerModules(createModules(SITE))
    }
}

private fun createModules(site: Site): ModuleRegistry {
    val moduleRegistry = site.moduleRegistry
    val commentQueryService = InMemoryDocumentElementQueryService(COMMENTS)
    val postQueryService = InMemoryDocumentQueryService(POSTS)
    val commentStorage = InMemoryStorageService(COMMENTS)

    val postModule = PostModule(
            site = site,
            postQueryService = postQueryService,
            operations = listOf(
                    PostLister(
                            site = site,
                            postQueryService = postQueryService),
                    PostRenderer(
                            site = site,
                            postQueryService = postQueryService),
                    CommentLister(
                            site = site,
                            commentQueryService = commentQueryService),
                    CommentCreator(
                            commentStorage = commentStorage)))

    val commentModule = CommentModule()

    moduleRegistry.register(postModule)
    moduleRegistry.register(commentModule)
    return moduleRegistry
}

private fun Routing.registerModules(moduleRegistry: ModuleRegistry) {
    get(SITE.baseUrl) {
        val session = call.sessions.get<AgoraSession>()
        val user = session?.let { User(it.userId, it.userFirstName, it.userLastName) }
        call.respondText(HOMEPAGE.render(SITE to user), ContentType.Text.Html)
    }
    logger.info("Registering modules...")
    moduleRegistry.modules.forEach { module ->
        module.whenHasOperation<PageLister> { documentListing ->
            logger.info("Registering module ${documentListing::class.simpleName} at ${documentListing.route}")
            get(documentListing.route) {
                call.respondText(
                        text = documentListing.renderListing(),
                        contentType = ContentType.Text.Html)
            }
        }
        module.whenHasOperation<AnyPageRenderer> { operation ->
            logger.info("Registering module ${operation::class.simpleName} at ${operation.route}")
            get(operation.route) {
                val permalink = PageURL.create(
                        klass = operation.permalinkType,
                        parameters = call.parameters)
                call.respondText(
                        text = operation.renderDocumentBy(permalink),
                        contentType = ContentType.Text.Html)
            }
        }
        module.whenHasOperation<AnyPageContentCreator> { operation ->
            logger.info("Registering module ${operation::class.simpleName} at ${operation.route}")
            post(operation.route) {
                operation.save(call.receiveParameters().mapTo(operation.creates))
                call.request.headers["Referer"]?.let { referer ->
                    call.respondRedirect(referer)
                }
            }
        }
    }
}