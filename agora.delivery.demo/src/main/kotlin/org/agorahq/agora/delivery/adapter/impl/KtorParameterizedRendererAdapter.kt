package org.agorahq.agora.delivery.adapter.impl

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.authorize
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.agorahq.agora.delivery.extensions.mapTo
import org.agorahq.agora.delivery.extensions.toOperationContext
import org.hexworks.cobalt.logging.api.LoggerFactory

class KtorParameterizedRendererAdapter<R : Resource, I : Any>(
        override val operation: Operation<R, I, String>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<R, I, String>, Operation<R, I, String> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module $name with route ${route}.")
        get(operation.route) {
            val input = call.parameters.mapTo(operation.inputClass)
            call.respondText(
                    contentType = ContentType.Text.Html,
                    text = authorization.authorize(call
                            .toOperationContext(site, authorization)
                            .withInput(input), operation).get().execute().get())
        }
    }
}
