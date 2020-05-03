package org.agorahq.agora.delivery.extensions

import io.ktor.application.ApplicationCall
import io.ktor.features.origin
import io.ktor.http.ContentType
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.delivery.data.AgoraSession
import org.agorahq.agora.delivery.data.AuthenticatedUserState

suspend fun ApplicationCall.tryRedirectToReferrer(site: SiteMetadata) {
    request.headers["Referer"]?.let { referer ->
        respondRedirect(referer)
    } ?: respondRedirect(site.baseUrl)
}

suspend fun ApplicationCall.redirectToRootWithMsg(site: SiteMetadata, message: Message) {
    sessions.set(agoraSession.withState(agoraSession.state.withMessage(message)))
    respondRedirect(site.baseUrl)
}

fun ApplicationCall.createRedirectFor(path: String): String {
    val defaultPort = if (request.origin.scheme == "http") 80 else 443
    val hostPort = request.host() + request.port().let { port -> if (port == defaultPort) "" else ":$port" }
    val protocol = request.origin.scheme
    return "$protocol://$hostPort$path"
}

val ApplicationCall.agoraSession: AgoraSession
    get() = sessions.get<AgoraSession>() ?: AgoraSession.anon().apply {
        sessions.set(this)
    }


fun ApplicationCall.toOperationContext(
        site: SiteMetadata,
        authorization: Authorization
): OperationContext {
    val user = when (val state = agoraSession.state) {
        is AuthenticatedUserState -> state.principal.toUser()
        else -> User.ANONYMOUS
    }
    return OperationContext.create(
            site = site,
            user = user,
            authorization = authorization,
            message = agoraSession.state.message
    )
}

suspend fun ApplicationCall.tryToRespondWithHtml(cmdResult: Result<out String, out Exception>) {
    respondText(
            text = cmdResult.get(), // TODO: proper error handling
            contentType = ContentType.Text.Html)
}
