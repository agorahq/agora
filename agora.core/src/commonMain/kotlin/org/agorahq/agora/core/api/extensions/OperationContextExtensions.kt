package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.PageElementFormRenderer
import org.agorahq.agora.core.api.operation.facets.PageElementListRenderer
import org.agorahq.agora.core.api.operation.facets.ShowsResourceCreatorLink
import org.agorahq.agora.core.api.operation.facets.ShowsResourceLink
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

fun OperationContext<out Any>.renderPageElementListsFor(parentId: String): String {
    val renderedPageElements = StringBuilder()
    site.forEachModuleHavingOperationWithFacet(PageElementListRenderer) { (_, renderer) ->
        val ctx: OperationContext<UUID> = withInput(parentId.toUUID())
        val result = ctx.authorization.authorizeAny(ctx, renderer).get().execute().get()
        renderedPageElements.append(result)
    }
    return renderedPageElements.toString()
}

fun OperationContext<out Any>.renderPageElementFormsFor(page: Page): String {
    val renderedPageElements = StringBuilder()
    if (user.isAuthenticated) {
        site.forEachModuleHavingOperationWithFacet(PageElementFormRenderer) { (_, renderer) ->
            val ctx: OperationContext<UUID> = withInput(page.id)
            val result = StringBuilder()
            ctx.authorization.authorizeAny(ctx, renderer)
                    .flatMap { it.execute() }
                    .map { result.append(it) }
            renderedPageElements.append(result.toString())
        }
    }
    return renderedPageElements.toString()
}

fun OperationContext<out Any>.renderResourceLinksFor(resourceClass: KClass<out Resource>, resourceId: String): String {
    val renderedElements = StringBuilder()
    // TODO: this can lead to problems because input is not checked!!
    site.forEachModuleHavingOperationWithFacet(ShowsResourceLink(resourceClass)) { (_, renderer) ->
        val ctx: OperationContext<out Any> = withInput(resourceId.toUUID())
        ctx.authorization.authorizeAny(ctx, renderer)
                .flatMap { it.execute() }
                .map { renderedElements.append(it) }
    }
    return renderedElements.toString()
}

fun OperationContext<out Any>.renderResourceCreatorLinksFor(resource: KClass<out Resource>): String {
    val elements = StringBuilder()
    // TODO: this can lead to problems because input is not checked!!
    site.forEachModuleHavingOperationWithFacet(ShowsResourceCreatorLink(resource)) { (_, renderer) ->
        authorization.authorizeAny(this, renderer)
                .flatMap {
                    it.execute()
                }.map {
                    elements.append(it)
                }
    }
    return elements.toString()
}
