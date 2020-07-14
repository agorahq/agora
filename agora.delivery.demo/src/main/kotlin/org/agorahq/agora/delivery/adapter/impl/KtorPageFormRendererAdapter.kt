package org.agorahq.agora.delivery.adapter.impl

import io.ktor.routing.Routing
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.hexworks.cobalt.logging.api.LoggerFactory

class KtorPageFormRendererAdapter<P : Page>(
        override val operation: Operation<P, P, String>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<P, P, String>, Operation<P, P, String> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module ${operation.name} with route ${operation.route}.")
        // TODO
    }
}
