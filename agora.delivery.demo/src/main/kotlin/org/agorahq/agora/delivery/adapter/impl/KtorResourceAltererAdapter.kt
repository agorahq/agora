package org.agorahq.agora.delivery.adapter.impl

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.request.receiveParameters
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.util.getOrFail
import io.ktor.util.pipeline.PipelineContext
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.authorize
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.agorahq.agora.delivery.extensions.toOperationContext
import org.agorahq.agora.delivery.extensions.tryRedirectToReferrer
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.logging.api.LoggerFactory

@Suppress("EXPERIMENTAL_API_USAGE")
class KtorResourceAltererAdapter<R : Resource>(
        override val operation: Operation<R, UUID, Unit>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<R, UUID, Unit>, Operation<R, UUID, Unit> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module $name at route ${route}.")
        post(route) {
            perform()
        }
    }

    private suspend fun PipelineContext<Unit, ApplicationCall>.perform() {
        val ctx = call.toOperationContext(site, authorization)
                .withInput(UUID.fromString(call.receiveParameters().getOrFail("id")))
        authorization.authorize(ctx, operation).get().execute().get()
        call.tryRedirectToReferrer(site)
    }
}
