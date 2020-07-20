@file:Suppress("UNCHECKED_CAST", "EXPERIMENTAL_API_USAGE", "unused")

package org.agorahq.agora.delivery

import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.auth.OAuthAccessTokenResponse.OAuth2
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.network.tls.certificates.generateCertificate
import io.ktor.request.receiveParameters
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.*
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.agorahq.agora.core.api.data.FormField
import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.core.api.data.MessageType
import org.agorahq.agora.core.api.extensions.isAuthenticated
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.OperationRegistry
import org.agorahq.agora.core.api.shared.security.BuiltInRoles
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.shared.templates.renderDefaultLoginPage
import org.agorahq.agora.core.api.viewmodel.UserRegistrationViewModel
import org.agorahq.agora.delivery.data.*
import org.agorahq.agora.delivery.extensions.*
import org.hexworks.cobalt.logging.api.LoggerFactory
import renderDefaultRegistrationPage
import java.io.File
import java.security.KeyStore

private val logger = LoggerFactory.getLogger("Application")
private val json = Json(JsonConfiguration.Stable)

private const val keyAlias = "mykey"
private const val PASSWORD = "changeit"
private val PASSWORD_ARR = PASSWORD.toCharArray()

private val PORT = System.getenv("PORT")?.toInt() ?: 8080

fun main(args: Array<String>) {
    val env = applicationEngineEnvironment {
        module {
            main()
        }
        connector {
            port = PORT
            host = "0.0.0.0"
        }
        if ((System.getenv("HEROKU")?.toBoolean() == true).not()) {
            addSSLConnector()
        }
    }
    embeddedServer(Netty, env).apply {
        start(wait = true)
    }
}

fun Application.main() {

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
        cookie<AgoraSession>(Cookies.AGORA_SESSION) {
            cookie.path = "/"
        }
    }

    install(Authentication) {

        session<AgoraSession>(Cookies.AGORA_SESSION) {
            challenge {
                call.respondRedirect("/login")
            }
            validate { session: AgoraSession ->
                when (val state = session.state) {
                    is AuthenticatedUserState -> state.principal
                    else -> error("User is not authenticated")
                }
            }
        }

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

        get("/login") {
            whenNotAuthenticated { ctx ->
                call.respondText(
                        text = Templates.htmlTemplate {
                            renderDefaultLoginPage(ctx)
                        },
                        contentType = ContentType.Text.Html)
            }
        }

        get("/logout") {
            logger.info("Logging user out.")
            call.sessions.set(AgoraSession.anon().withMessage(Message(
                    type = MessageType.INFO,
                    text = "You've been successfully logged out."
            )))
            call.respondRedirect(SITE.baseUrl)
        }

        get("/register") {
            whenNotAuthenticated { ctx ->
                val session = call.sessions.get<AgoraSession>() ?: error("No session present")
                when (val state = session.state) {
                    is AuthenticatedUserState -> {
                        logger.info("User is already authenticated, no need to register.")
                        call.respondRedirect(SITE.baseUrl)
                    }
                    is RegisteringState -> {
                        logger.info("Rendering registration form...")
                        call.respondText(
                                text = Templates.htmlTemplate {
                                    renderDefaultRegistrationPage(ctx, state.toUserRegistrationModel())
                                },
                                contentType = ContentType.Text.Html)
                    }
                }
            }
        }

        post("/register") {
            whenNotAuthenticated { ctx ->
                val params = call.receiveParameters()
                val username = params.mapTo(UserRegistrationParams::class).username
                val session = call.sessions.get<AgoraSession>() ?: error("No session present")
                when (val state = session.state) {
                    is RegisteringState -> {
                        val model = UserRegistrationViewModel(
                                email = state.oauthUser.email,
                                firstName = state.oauthUser.firstName,
                                lastName = state.oauthUser.lastName,
                                username = FormField.Dirty(username)
                        ).validate()
                        if (model.isValid) {
                            val user = User.create(
                                    email = model.email,
                                    username = model.username.value,
                                    firstName = model.firstName,
                                    lastName = model.lastName,
                                    roles = setOf(BuiltInRoles.ATTENDEE)
                            )
                            Services.userStorage.create(user)
                            call.sessions.set(AgoraSession.fromUser(user))
                            call.respondRedirect(SITE.baseUrl)
                        } else {
                            logger.info("Model is not valid, re-rendering registration page.")
                            call.respondText(
                                    text = Templates.htmlTemplate {
                                        renderDefaultRegistrationPage(ctx, model)
                                    },
                                    contentType = ContentType.Text.Html)
                        }
                    }
                    else -> call.respondRedirect(SITE.baseUrl)
                }
            }
        }

        authenticate("google") {
            route("/login/google") {
                handle {
                    handleOauth { principal ->
                        HttpClient(Apache).get<String>("https://www.googleapis.com/userinfo/v2/me") {
                            header("Authorization", "Bearer ${principal.accessToken}")
                        }.deserializeTo(GoogleUser.serializer())
                    }
                }
            }
        }
        authenticate("facebook") {
            route("/login/facebook") {
                handle {
                    handleOauth { principal ->
                        val token = principal.accessToken
                        val id = HttpClient(Apache).get<String>("https://graph.facebook.com/me") {
                            header("Authorization", "Bearer $token")
                        }.deserializeTo(FacebookUserId.serializer()).id
                        val url = "https://graph.facebook.com/$id?fields=first_name,last_name,email,id&access_token=$token"
                        HttpClient(Apache)
                                .get<String>(url)
                                .deserializeTo(FacebookUser.serializer())
                    }
                }
            }
        }
        registerOperations(SITE.operationRegistry)
        registerAdapters(SITE, AUTHORIZATION)
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.whenNotAuthenticated(
        fn: suspend (OperationContext<out Any>) -> Unit
) {
    val ctx = call.toOperationContext(SITE, AUTHORIZATION)
    if (ctx.user.isAuthenticated) {
        logger.info("User is already authenticated, redirecting to referrer.")
        call.tryRedirectToReferrer(SITE)
    } else {
        fn(ctx)
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.handleOauth(
        fn: suspend (OAuth2) -> OauthUser
) {
    whenNotAuthenticated {
        val principal: OAuth2 = call.authentication.principal()
                ?: error("No principal")
        val oauthUser = fn(principal)
        val user = Services.userQueryService.findOneBy(User::email, oauthUser.email)
        if (user.isPresent) {
            logger.info("User is already registered, redirecting to front page.")
            call.sessions.set(AgoraSession.fromUser(user.get()))
            call.respondRedirect(SITE.baseUrl)
        } else {
            logger.info("User is not yet registered, redirecting to registration.")
            call.sessions.set(AgoraSession.fromOauthUser(oauthUser))
            call.respondRedirect("/register")
        }
    }
}

private fun registerOperations(operationRegistry: OperationRegistry): OperationRegistry {

    operationRegistry.register(Operations.listPosts)
    operationRegistry.register(Operations.showPost)
    operationRegistry.register(Operations.createPost)
    operationRegistry.register(Operations.showPostEditor)
    operationRegistry.register(Operations.createAndEditNewPost)
    operationRegistry.register(Operations.deletePost)

    operationRegistry.register(Operations.showCreatePostLink)
    operationRegistry.register(Operations.showTogglePostPublished)
    operationRegistry.register(Operations.showEditPost)
    operationRegistry.register(Operations.showDeletePost)

    operationRegistry.register(Operations.listComments)
    operationRegistry.register(Operations.showCommentEditor)
    operationRegistry.register(Operations.showEditCommentLink)
    operationRegistry.register(Operations.createComment)
    operationRegistry.register(Operations.deleteComment)
    operationRegistry.register(Operations.togglePostPublished)

    return operationRegistry
}

private fun RegisteringState.toUserRegistrationModel() = UserRegistrationViewModel(
        email = oauthUser.email,
        firstName = oauthUser.firstName,
        lastName = oauthUser.lastName
)

private fun ApplicationEngineEnvironmentBuilder.addSSLConnector() {
    val keyStoreFile = File.createTempFile("cert-", ".jks")
    generateCertificate(
            file = keyStoreFile,
            keyAlias = keyAlias,
            keyPassword = PASSWORD
    )
    val keyStore = KeyStore.getInstance("JKS")
    keyStore.load(keyStoreFile.inputStream(), PASSWORD_ARR)
    sslConnector(
            keyStore = keyStore,
            keyAlias = keyAlias,
            keyStorePassword = { PASSWORD_ARR },
            privateKeyPassword = { PASSWORD_ARR }
    ) {
        port = 8443
        keyStorePath = keyStoreFile.absoluteFile
    }
}
