package org.agorahq.agora.delivery.adapter.impl

import io.ktor.application.call
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.delivery.adapter.KtorOperationAdapter
import org.agorahq.agora.delivery.extensions.toPageURLContext
import org.agorahq.agora.delivery.extensions.tryToRespondWithHtml
import org.hexworks.cobalt.logging.api.LoggerFactory

class KtorPageRendererAdapter<P : Page>(
        override val operation: Operation<P, PageURLContext<P>, String>,
        private val site: SiteMetadata,
        private val authorization: Authorization
) : KtorOperationAdapter<P, PageURLContext<P>, String>, Operation<P, PageURLContext<P>, String> by operation {

    private val logger = LoggerFactory.getLogger(this::class)

    override fun Routing.register() {
        logger.info("Registering module $name with route ${route}.")
        get(operation.route) {
            call.tryToRespondWithHtml(
                    call.toPageURLContext(site, authorization, urlClass)
                            .createCommand()
                            .execute())
        }
    }
}
