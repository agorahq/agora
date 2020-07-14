package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.PageElementFormRenderer
import org.agorahq.agora.core.api.operation.facets.PageElementListRenderer

fun OperationContext<out Any>.renderPageElementListsFor(page: Page): String {
    val renderedPageElements = StringBuilder()
    site.forEachModuleHavingOperationWithFacet(PageElementListRenderer) { (_, renderer) ->
        val ctx: OperationContext<Page> = withInput(page)
        val result = ctx.authorization.authorizeAny(ctx, renderer).get().execute().get()
        renderedPageElements.append(result)
    }
    return renderedPageElements.toString()
}

fun OperationContext<out Any>.renderPageElementFormsFor(page: Page): String {
    val renderedPageElements = StringBuilder()
    if (user.isAuthenticated) {
        site.forEachModuleHavingOperationWithFacet(PageElementFormRenderer) { (_, renderer) ->
            val ctx: OperationContext<out Any> = withInput(page)
            val result = ctx.authorization.authorizeAny(ctx, renderer).get().execute().get()
            renderedPageElements.append(result)
        }
    }
    return renderedPageElements.toString()
}
