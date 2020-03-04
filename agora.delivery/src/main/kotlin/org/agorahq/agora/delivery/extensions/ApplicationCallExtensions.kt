package org.agorahq.agora.delivery.extensions

import io.ktor.application.ApplicationCall
import io.ktor.features.origin
import io.ktor.request.host
import io.ktor.request.port
import io.ktor.response.respondRedirect
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.data.UserMetadata
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.delivery.Session

suspend fun ApplicationCall.tryRedirectToReferrer(site: SiteMetadata) {
    request.headers["Referer"]?.let { referer ->
        respondRedirect(referer)
    } ?: respondRedirect(site.baseUrl)
}

fun ApplicationCall.createRedirectFor(path: String): String {
    val defaultPort = if (request.origin.scheme == "http") 80 else 443
    val hostPort = request.host() + request.port().let { port -> if (port == defaultPort) "" else ":$port" }
    val protocol = request.origin.scheme
    return "$protocol://$hostPort$path"
}

fun ApplicationCall.toOperationContext(
        site: SiteMetadata
): OperationContext {
    val session = sessions.get<Session>()
    val user = session?.toUser() ?: UserMetadata.ANONYMOUS
    return OperationContext.create(site, user)
}
