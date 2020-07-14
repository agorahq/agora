package org.agorahq.agora.delivery.adapter.impl

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.authorize
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.agorahq.agora.delivery.extensions.toOperationContext
import org.agorahq.agora.delivery.extensions.toResourceURL
import org.hexworks.cobalt.logging.api.LoggerFactory

class KtorPageRendererAdapter<P : Page>(
        override val operation: Operation<P, ResourceURL<P>, String>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<P, ResourceURL<P>, String>, Operation<P, ResourceURL<P>, String> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module $name with route ${route}.")
        get(operation.route) {
            val url = call.parameters.toResourceURL(operation.urlClass)
            call.respondText(
                    contentType = ContentType.Text.Html,
                    text = authorization.authorize(call.toOperationContext(
                            site = site,
                            authorization = authorization
                    ).withInput(url), operation).get().execute().get())
        }
    }
}
