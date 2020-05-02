@file:Suppress("UNCHECKED_CAST", "EXPERIMENTAL_API_USAGE", "unused")

package org.agorahq.agora.delivery

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.agorahq.agora.comment.converter.CommentConverter
import org.agorahq.agora.comment.module.CommentModule
import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.comment.operations.DeleteComment
import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.comment.operations.ShowCommentForm
import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.FormField.Valid
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.data.Validators
import org.agorahq.agora.core.api.extensions.isAuthenticated
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationType.*
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.ModuleRegistry
import org.agorahq.agora.core.api.service.impl.InMemoryPageElementQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryPageQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryQueryService
import org.agorahq.agora.core.api.service.impl.InMemoryStorageService
import org.agorahq.agora.core.api.shared.templates.DEFAULT_REGISTRATION_PAGE
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HOMEPAGE
import org.agorahq.agora.core.api.shared.templates.DEFAULT_LOGIN_PAGE
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.api.viewmodel.UserRegistrationViewModel
import org.agorahq.agora.delivery.data.FacebookUser
import org.agorahq.agora.delivery.data.FacebookUserId
import org.agorahq.agora.delivery.data.GoogleUser
import org.agorahq.agora.delivery.data.Session
import org.agorahq.agora.delivery.extensions.*
import org.agorahq.agora.post.converter.PostConverter
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostModule
import org.agorahq.agora.post.operations.CreatePost
import org.agorahq.agora.post.operations.DeletePost
import org.agorahq.agora.post.operations.ListPosts
import org.agorahq.agora.post.operations.ShowPost
import org.hexworks.cobalt.logging.api.LoggerFactory

private val logger = LoggerFactory.getLogger("Application")
private val json = Json(JsonConfiguration.Stable)

fun main(args: Array<String>) {
    EngineMain.main(args)
}

val userQueryService = InMemoryQueryService(mapOf(
        JACK.id to JACK, JENNA.id to JENNA, FRANK.id to FRANK, OGABI.id to OGABI)
        .toMutableMap())

val converterService = ConverterService.create(listOf(
        CommentConverter(userQueryService), PostConverter(userQueryService)))

val commentQueryService = InMemoryPageElementQueryService(COMMENTS)
val postQueryService = InMemoryPageQueryService(Post::class, POSTS)
val commentStorage = InMemoryStorageService(COMMENTS)
val postStorage = InMemoryStorageService(POSTS)

val listPosts = ListPosts(postQueryService, converterService)
val renderPost = ShowPost(postQueryService, converterService)
val createPost = CreatePost(postStorage, converterService)
val deletePost = DeletePost(postStorage)

val listComments = ListComments(commentQueryService, converterService)
val renderCommentForm = ShowCommentForm()
val createComment = CreateComment(commentStorage, converterService)
val deleteComment = DeleteComment(commentStorage)

val postModule = PostModule(listOf(listPosts, renderPost, listComments))
val commentModule = CommentModule(listOf(renderCommentForm, createComment))

fun Application.module() {

    val googleOauthProvider = OAuthServerSettings.OAuth2ServerSettings(
            name = "google",
            authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
            accessTokenUrl = "https://www.googleapis.com/oauth2/v3/token",
            requestMethod = HttpMethod.Post,

            clientId = GOOGLE_CLIENT_ID,
            clientSecret = GOOGLE_CLIENT_SECRET,
            defaultScopes = listOf("profile", "email")  // no email, but gives full name, picture, and id
    )

    val facebookOauthProvider = OAuthServerSettings.OAuth2ServerSettings(
            name = "facebook",
            authorizeUrl = "https://graph.facebook.com/oauth/authorize",
            accessTokenUrl = "https://graph.facebook.com/oauth/access_token",
            requestMethod = HttpMethod.Post,

            clientId = FACEBOOK_CLIENT_ID,
            clientSecret = FACEBOOK_CLIENT_SECRET,
            defaultScopes = listOf("public_profile", "email")
    )

    install(Sessions) {
        cookie<Session>("auth") {
            cookie.path = "/"
        }
    }

    install(Authentication) {
        oauth("google") {
            client = HttpClient(Apache)
            providerLookup = { googleOauthProvider }
            urlProvider = { createRedirectFor("/login/google") }
        }
        oauth("facebook") {
            client = HttpClient(Apache)
            providerLookup = { facebookOauthProvider }
            urlProvider = { createRedirectFor("/login/facebook") }
        }
    }

    routing {

        get("/logout") {
            call.sessions.clear<Session>()
            call.tryRedirectToReferrer(SITE)
        }

        get("/login") {
            val ctx = call.toOperationContext(SITE, AUTHORIZATION)
            call.respondText(
                    text = DEFAULT_LOGIN_PAGE.render(ctx),
                    contentType = ContentType.Text.Html)
        }

        get("/register") {
            val ctx = call.toOperationContext(SITE, AUTHORIZATION)
            val model = UserRegistrationViewModel(
                    context = ctx,
                    email = Valid(ctx.user.email, Validators.email))
            call.respondText(
                    text = DEFAULT_REGISTRATION_PAGE.render(model),
                    contentType = ContentType.Text.Html)
        }

        post("/register") {
            call.respondRedirect(SITE.baseUrl)
        }

        authenticate("google") {
            route("/login/google") {
                handle {
                    val ctx = call.toOperationContext(SITE, AUTHORIZATION)
                    if (ctx.user.isAuthenticated) {
                        call.tryRedirectToReferrer(SITE)
                    } else {
                        val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                                ?: error("No principal")

                        val data = HttpClient(Apache).get<String>("https://www.googleapis.com/userinfo/v2/me") {
                            header("Authorization", "Bearer ${principal.accessToken}")
                        }
                        val oauthUser = data.deserializeTo(GoogleUser.serializer())

                        val user = userQueryService.findOneBy(User::email, oauthUser.email)
                        if (user.isPresent) {
                            call.sessions.set(Session.fromUser(user.get()))
                            call.tryRedirectToReferrer(SITE)
                        } else {
                            call.sessions.set(Session.fromOauthUser(oauthUser))
                            call.respondRedirect("/register")
                        }
                    }
                }
            }
        }
        authenticate("facebook") {
            route("/login/facebook") {
                handle {
                    val ctx = call.toOperationContext(SITE, AUTHORIZATION)
                    if (ctx.user.isAuthenticated) {
                        call.tryRedirectToReferrer(SITE)
                    } else {
                        val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                                ?: error("No principal")

                        val id = HttpClient(Apache).get<String>("https://graph.facebook.com/me") {
                            header("Authorization", "Bearer ${principal.accessToken}")
                        }.deserializeTo(FacebookUserId.serializer()).id
                        val token = principal.accessToken
                        val url = "https://graph.facebook.com/$id?fields=first_name,last_name,email,id&access_token=$token"
                        val oauthUser = HttpClient(Apache).get<String>(url).deserializeTo(FacebookUser.serializer())

                        val user = userQueryService.findOneBy(User::email, oauthUser.email)
                        if (user.isPresent) {
                            call.sessions.set(Session.fromUser(user.get()))
                            call.tryRedirectToReferrer(SITE)
                        } else {
                            call.sessions.set(Session.fromOauthUser(oauthUser))
                            call.respondRedirect("/register")
                        }
                    }
                }
            }
        }
        registerModules(createModules(SITE))
    }
}

private fun createModules(site: SiteMetadata): ModuleRegistry {

    val moduleRegistry = site.moduleRegistry

    moduleRegistry.register(postModule)
    moduleRegistry.register(commentModule)

    return moduleRegistry
}

private fun Routing.registerModules(moduleRegistry: ModuleRegistry) {

    get(SITE.baseUrl) {
        call.respondText(DEFAULT_HOMEPAGE.render(call.toOperationContext(SITE, AUTHORIZATION)), ContentType.Text.Html)
    }
    logger.info("Registering modules...")
    moduleRegistry.modules.forEach { module ->

        module.findMatchingOperations(PageRenderer(Page::class)).forEach { renderer: Operation<Page, PageURLContext<Page>, String> ->
            logger.info("Registering module ${renderer.name} with route ${renderer.route}.")
            get(renderer.route) {
                with(renderer) {
                    call.tryToRespondWithHtml(call.toOperationContext(SITE, AUTHORIZATION)
                            .toPageURLContext(ResourceURL.create(
                                    urlClass = renderer.urlClass,
                                    parameters = call.parameters))
                            .createCommand()
                            .execute())
                }
            }
        }

        module.findMatchingOperations(PageListRenderer(Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} with route ${renderer.route}.")
            get(renderer.route) {
                call.respondText(
                        text = with(renderer) {
                            call.toOperationContext(SITE, AUTHORIZATION).createCommand().execute().get()
                        },
                        contentType = ContentType.Text.Html)
            }
        }

        module.findMatchingOperations(PageFormRenderer(Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} at route ${renderer.route}.")
            // TODO
        }

        module.findMatchingOperations(PageElementFormRenderer(Resource::class, Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} at route ${renderer.route}.")
            // TODO
        }

        module.findMatchingOperations(PageElementListRenderer(Resource::class, Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} at route ${renderer.route}.")
            // TODO
        }

        module.findMatchingOperations(ResourceSaver(Resource::class, ViewModel::class)).forEach { saver ->
            logger.info("Registering module ${saver.name} at route ${saver.route}.")
            post(saver.route) {
                val modelClass = converterService.findViewModelClassFor(saver.resourceClass)
                with(saver) {
                    call.toOperationContext(SITE, AUTHORIZATION)
                            .toViewModelContext(call.receiveParameters().mapTo(modelClass))
                            .createCommand().execute().get()
                }
                call.tryRedirectToReferrer(SITE)
            }
        }

        module.findMatchingOperations(ResourceDeleter(Resource::class)).forEach { deleter ->
            logger.info("Registering module ${deleter.name} at route ${deleter.route}.")
            // TODO 
        }

    }
}

