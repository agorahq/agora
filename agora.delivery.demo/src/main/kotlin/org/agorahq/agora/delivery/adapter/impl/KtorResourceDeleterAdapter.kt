package org.agorahq.agora.delivery.adapter.impl

import io.ktor.application.call
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.util.getOrFail
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.agorahq.agora.delivery.extensions.toOperationContext
import org.agorahq.agora.delivery.extensions.toResourceIdContext
import org.agorahq.agora.delivery.extensions.tryRedirectToReferrer
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.logging.api.LoggerFactory

@Suppress("EXPERIMENTAL_API_USAGE")
class KtorResourceDeleterAdapter<R : Resource>(
        override val operation: Operation<R, ResourceIdContext, Unit>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<R, ResourceIdContext, Unit>, Operation<R, ResourceIdContext, Unit> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module $name at route ${route}.")
        delete(route) {
            val ctx = call.toResourceIdContext(site, authorization, UUID.fromString(call.parameters.getOrFail("id")))
            authorization.authorize(ctx, operation).get().execute().get()
            call.tryRedirectToReferrer(site)
        }
    }
}
