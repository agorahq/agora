package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.PageElement
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.OperationType.PageElementFormRenderer
import org.agorahq.agora.core.api.security.OperationType.PageElementListRenderer

fun OperationContext.renderPageElementListsFor(page: Page): String {
    val renderedPageElements = StringBuilder()
    site.forEachModuleHavingOperationWithType(PageElementListRenderer(PageElement::class, Page::class)) { (_, renderer) ->
        with(renderer) {
            val ctx = toPageContext(page)
            val (_, user, auth) = ctx
            if (auth.hasGroupAccess(user, renderer)) {
                renderedPageElements.append(ctx.createCommand().execute().get())
            }
        }
    }
    return renderedPageElements.toString()
}

fun OperationContext.renderPageElementFormsFor(page: Page): String {
    val renderedPageElements = StringBuilder()
    if (user.isAuthenticated) {
        site.forEachModuleHavingOperationWithType(PageElementFormRenderer(PageElement::class, Page::class)) { (_, renderer) ->
            with(renderer) {
                val ctx = toPageContext(page)
                val (_, user, auth) = ctx
                if (auth.hasGroupAccess(user, renderer)) {
                    renderedPageElements.append(ctx.createCommand().execute().get())
                }
            }
        }
    }
    return renderedPageElements.toString()
}
