package org.agorahq.agora.delivery.adapter.impl

import io.ktor.routing.Routing
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.hexworks.cobalt.logging.api.LoggerFactory

class KtorResourceDeleterAdapter<R : Resource>(
        override val operation: Operation<R, ResourceIdContext, Unit>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<R, ResourceIdContext, Unit>, Operation<R, ResourceIdContext, Unit> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        // TODO
    }
}
