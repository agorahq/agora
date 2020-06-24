package org.agorahq.agora.delivery.adapter.impl

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.agorahq.agora.delivery.extensions.toOperationContext
import org.hexworks.cobalt.logging.api.LoggerFactory

class KtorPageListRendererAdapter<P : Page>(
        override val operation: Operation<P, OperationContext, String>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<P, OperationContext, String>, Operation<P, OperationContext, String> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module ${operation.name} with route ${operation.route}.")
        get(operation.route) {
            call.respondText(
                    text = call
                            .toOperationContext(site, authorization)
                            .createCommand().execute().get(),
                    contentType = ContentType.Text.Html)
        }
    }
}
