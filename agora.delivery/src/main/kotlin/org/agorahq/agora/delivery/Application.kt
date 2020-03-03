@file:Suppress("UNCHECKED_CAST", "EXPERIMENTAL_API_USAGE", "unused")

package org.agorahq.agora.delivery

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.Application
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
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.request.receiveParameters
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
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.hex
import org.agorahq.agora.comment.converter.CommentConverter
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.comment.operations.DeleteComment
import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.comment.operations.ShowCommentForm
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.extensions.AnyPageRenderer
import org.agorahq.agora.core.api.extensions.AnyResourceListRenderer
import org.agorahq.agora.core.api.extensions.AnyResourceOperation
import org.agorahq.agora.core.api.extensions.toResourceContext
import org.agorahq.agora.core.api.extensions.tryToExecute
import org.agorahq.agora.core.api.extensions.whenHasOperation
import org.agorahq.agora.core.api.service.ModuleRegistry
import org.agorahq.agora.core.api.service.impl.InMemoryChildResourceQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryPageQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryStorageService
import org.agorahq.agora.core.api.shared.templates.HOMEPAGE
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.delivery.extensions.create
import org.agorahq.agora.delivery.extensions.createRedirectFor
import org.agorahq.agora.delivery.extensions.toOperationContext
import org.agorahq.agora.delivery.extensions.toResource
import org.agorahq.agora.delivery.extensions.tryRedirectToReferrer
import org.agorahq.agora.delivery.security.BuiltInRoles.ADMIN
import org.agorahq.agora.delivery.security.BuiltInRoles.ATTENDEE
import org.agorahq.agora.post.converter.PostConverter
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.agorahq.agora.post.operations.CreatePost
import org.agorahq.agora.post.operations.DeletePost
import org.agorahq.agora.post.operations.ListPosts
import org.agorahq.agora.post.operations.ShowPost
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.logging.api.LoggerFactory

private val logger = LoggerFactory.getLogger("Application")

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
        cookie<Session>("oauthSampleSessionId") {
            val secretSignKey = hex("000102030405060708090a0b0c0d0e0f")
            transform(SessionTransportTransformerMessageAuthentication(secretSignKey))
        }
    }

    install(Authentication) {
        oauth("google-oauth") {
            client = HttpClient(Apache)
            providerLookup = { googleOauthProvider }
            urlProvider = { createRedirectFor("/login") }
        }
    }

    routing {

        get("/logout") {
            call.sessions.clear<Session>()
            call.tryRedirectToReferrer(SITE)
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
                        call.sessions.set(Session(
                                userId = UUID.randomUUID().toString(),
                                userEmail = it,
                                userNick = data["given_name"]?.toString() ?: "anonymous",
                                role = if (it == "arold.adam@gmail.com") ADMIN.name else ATTENDEE.name))
                    } ?: error("An email address is mandatory for a User to log in.")
                    call.tryRedirectToReferrer(SITE)
                }
            }
        }
        registerModules(createModules(SITE))
    }
}

private fun createModules(site: SiteMetadata): ModuleRegistry {

    val moduleRegistry = site.moduleRegistry

    val commentQueryService = InMemoryChildResourceQueryService(COMMENTS)
    val postQueryService = InMemoryPageQueryService(Post::class, POSTS)
    val commentStorage = InMemoryStorageService(COMMENTS)
    val postStorage = InMemoryStorageService(POSTS)

    val listPosts = ListPosts(postQueryService)
    val renderPost = ShowPost(postQueryService)
    val createPost = CreatePost(postStorage)
    val deletePost = DeletePost(postStorage)

    val listComments = ListComments(commentQueryService)
    val renderCommentForm = ShowCommentForm()
    val createComment = CreateComment(commentStorage)
    val deleteComment = DeleteComment(commentStorage)

    val postModule = PostModule(listOf(listPosts, renderPost, listComments))
    val commentModule = CommentModule(listOf(renderCommentForm, createComment))

    moduleRegistry.register(postModule)
    moduleRegistry.register(commentModule)

    return moduleRegistry
}

private fun Routing.registerModules(moduleRegistry: ModuleRegistry) {

    val userQueryService = InMemoryQueryService(mapOf(
            JACK.id to JACK, JENNA.id to JENNA, FRANK.id to FRANK, EDEM.id to EDEM, OGABI.id to OGABI)
            .toMutableMap())

    val converterService = ConverterService.create(listOf(
            CommentConverter(userQueryService), PostConverter(userQueryService)))

    get(SITE.baseUrl) {
        call.respondText(HOMEPAGE.render(call.toOperationContext(SITE, converterService)), ContentType.Text.Html)
    }
    logger.info("Registering modules...")
    moduleRegistry.modules.forEach { module ->

        module.whenHasOperation<AnyResourceListRenderer> { documentListing ->
            logger.info("Registering module ${documentListing::class.simpleName} at ${documentListing.route}")
            get(documentListing.route) {
                call.respondText(
                        text = with(documentListing) { call.toOperationContext(SITE, converterService).reify().execute().get() },
                        contentType = ContentType.Text.Html)
            }
        }

        module.whenHasOperation<AnyPageRenderer> { renderer ->
            logger.info("Registering module ${renderer::class.simpleName} at ${renderer.route}")
            get(renderer.route) {
                val ctx = with(call.toOperationContext(SITE, converterService)) {
                    ResourceURL.create(
                            klass = renderer.urlType,
                            parameters = call.parameters).toPageRequestContext()

                }
                call.respondText(
                        text = with(renderer) { ctx.reify().execute().get() },
                        contentType = ContentType.Text.Html)
            }
        }

        module.whenHasOperation<AnyResourceOperation> { op ->
            logger.info("Registering module ${op::class.simpleName} at ${op.route}")
            post(op.route) {
                call.receiveParameters()
                        .toResource(converterService, op.resourceClass)
                        .toResourceContext(call.toOperationContext(SITE, converterService))
                        .tryToExecute(op)
                call.tryRedirectToReferrer(SITE)
            }
        }

    }
}