package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.operation.OperationType.PageElementFormRenderer
import org.agorahq.agora.core.api.operation.OperationType.PageElementListRenderer
import org.agorahq.agora.core.api.operation.context.OperationContext

fun OperationContext.renderPageElementListsFor(page: Page): String {
    val renderedPageElements = StringBuilder()
    site.forEachModuleHavingOperationWithType(PageElementListRenderer(PageElement::class, Page::class)) { (_, renderer) ->
        val ctx = toPageContext(page)
        val result = ctx.authorization.authorize(ctx, renderer).get().execute().get()
        renderedPageElements.append(result)
    }
    return renderedPageElements.toString()
}

fun OperationContext.renderPageElementFormsFor(page: Page): String {
    val renderedPageElements = StringBuilder()
    if (user.isAuthenticated) {
        site.forEachModuleHavingOperationWithType(PageElementFormRenderer(PageElement::class, Page::class)) { (_, renderer) ->
            val ctx = toPageContext(page)
            val result = ctx.authorization.authorize(ctx, renderer).get().execute().get()
            renderedPageElements.append(result)
        }
    }
    return renderedPageElements.toString()
}
