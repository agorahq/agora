package org.agorahq.agora.delivery.adapter.impl

import io.ktor.application.call
import io.ktor.request.receiveParameters
import io.ktor.routing.Routing
import io.ktor.routing.post
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.delivery.Services
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.agorahq.agora.delivery.extensions.mapTo
import org.agorahq.agora.delivery.extensions.toOperationContext
import org.agorahq.agora.delivery.extensions.tryRedirectToReferrer
import org.hexworks.cobalt.logging.api.LoggerFactory

class KtorResourceSaverAdapter<R : Resource, V : ViewModel>(
        override val operation: Operation<R, ViewModelContext<V>, Unit>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<R, ViewModelContext<V>, Unit>, Operation<R, ViewModelContext<V>, Unit> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module $name at route ${route}.")
        post(route) {
            val modelClass = Services.converterService.findViewModelClassFor<R, V>(resourceClass)
            call.toOperationContext(site, authorization)
                    .toViewModelContext(call.receiveParameters().mapTo(modelClass))
                    .createCommand().execute().get()
            call.tryRedirectToReferrer(site)
        }
    }
}
