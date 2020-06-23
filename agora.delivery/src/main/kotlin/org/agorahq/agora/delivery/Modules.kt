package org.agorahq.agora.delivery

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receiveParameters
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.security.OperationType
import org.agorahq.agora.core.api.service.ModuleRegistry
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HOMEPAGE
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.delivery.extensions.*
import org.hexworks.cobalt.logging.api.LoggerFactory

private val logger = LoggerFactory.getLogger("Modules")

fun Routing.registerModules(moduleRegistry: ModuleRegistry) {

    get(SITE.baseUrl) {
        call.respondText(DEFAULT_HOMEPAGE.render(call.toOperationContext(SITE, AUTHORIZATION)), ContentType.Text.Html)
    }
    logger.info("Registering modules...")
    moduleRegistry.modules.forEach { module ->

        module.findMatchingOperations(OperationType.PageRenderer(Page::class)).forEach { pageRenderer: Operation<Page, PageURLContext<Page>, String> ->
            logger.info("Registering module ${pageRenderer.name} with route ${pageRenderer.route}.")
            get(pageRenderer.route) {
                with(pageRenderer) {
                    call.tryToRespondWithHtml(
                            call.toPageURLContext(SITE, AUTHORIZATION, pageRenderer.urlClass)
                            .createCommand()
                            .execute())
                }
            }
        }

        module.findMatchingOperations(OperationType.PageListRenderer(Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} with route ${renderer.route}.")
            get(renderer.route) {
                call.respondText(
                        text = with(renderer) {
                            call.toOperationContext(SITE, AUTHORIZATION).createCommand().execute().get()
                        },
                        contentType = ContentType.Text.Html)
            }
        }

        module.findMatchingOperations(OperationType.PageFormRenderer(Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} at route ${renderer.route}.")
            // TODO
        }

        module.findMatchingOperations(OperationType.PageElementFormRenderer(Resource::class, Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} at route ${renderer.route}.")
            // TODO
        }

        module.findMatchingOperations(OperationType.PageElementListRenderer(Resource::class, Page::class)).forEach { renderer ->
            logger.info("Registering module ${renderer.name} at route ${renderer.route}.")
            // TODO
        }

        module.findMatchingOperations(OperationType.ResourceSaver(Resource::class, ViewModel::class)).forEach { saver ->
            logger.info("Registering module ${saver.name} at route ${saver.route}.")
            post(saver.route) {
                val modelClass = Services.converterService.findViewModelClassFor(saver.resourceClass)
                with(saver) {
                    call.toOperationContext(SITE, AUTHORIZATION)
                            .toViewModelContext(call.receiveParameters().mapTo(modelClass))
                            .createCommand().execute().get()
                }
                call.tryRedirectToReferrer(SITE)
            }
        }

        module.findMatchingOperations(OperationType.ResourceDeleter(Resource::class)).forEach { deleter ->
            logger.info("Registering module ${deleter.name} at route ${deleter.route}.")
            // TODO
        }

    }
}
