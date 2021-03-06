package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Attribute
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.*
import org.agorahq.agora.core.api.security.authorize
import org.hexworks.cobalt.core.api.UUID
import kotlin.reflect.KClass

inline fun <reified R : Resource, reified I : Any, reified O : Any> OperationContext<out Any>.findMatchingOperations(
        vararg attributes: Attribute
) = site.findMatchingOperations<R, I, O>(*attributes)

inline fun <reified A : Attribute> OperationContext<out Any>.findOperationsWithAttribute() =
        site.findOperationsWithAttribute<A>()

fun OperationContext<out Any>.renderPageElementsFor(parentId: String): String {
    val renderedPageElements = StringBuilder()
    site.findMatchingOperations<Resource, UUID, String>(ShowsPageElements).forEach { renderer ->
        val ctx: OperationContext<UUID> = withInput(parentId.toUUID())
        val result = ctx.authorization.authorize(ctx, renderer).get().execute().get()
        renderedPageElements.append(result)
    }
    return renderedPageElements.toString()
}

fun OperationContext<out Any>.renderFormsFor(
        id: String,
        resourceClass: KClass<out Resource> = Resource::class
): String {
    val renderedContent = StringBuilder()
    site.findMatchingOperations<Resource, UUID, String>(RendersForm(resourceClass)).forEach { renderer ->
        val ctx: OperationContext<UUID> = withInput(id.toUUID())
        val result = StringBuilder()
        ctx.authorization.authorize(ctx, renderer)
                .flatMap { it.execute() }
                .map { result.append(it) }
        renderedContent.append(result.toString())
    }
    return renderedContent.toString()
}

fun OperationContext<out Any>.renderPageElementFormsFor(
        parentId: UUID,
        elementClass: KClass<out PageElement> = PageElement::class
): String {
    val renderedPageElements = StringBuilder()
    site.findMatchingOperations<PageElement, UUID, String>(RendersPageElementForm(elementClass)).forEach { renderer ->
        val ctx: OperationContext<UUID> = withInput(parentId)
        val result = StringBuilder()
        ctx.authorization.authorize(ctx, renderer)
                .flatMap { it.execute() }
                .map { result.append(it) }
        renderedPageElements.append(result.toString())
    }
    return renderedPageElements.toString()
}

fun OperationContext<out Any>.renderResourceLinksFor(resourceClass: KClass<out Resource>, resourceId: String): String {
    val renderedElements = StringBuilder()
    // TODO: this can lead to problems because input is not checked!!
    site.findMatchingOperations<Resource, Any, String>(ShowsResourceLink(resourceClass)).forEach { renderer ->
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
    site.findMatchingOperations<Resource, Any, String>(ShowsResourceCreatorLink(resource)).forEach { renderer ->
        authorization.authorizeAny(this, renderer)
                .flatMap {
                    it.execute()
                }.map {
                    elements.append(it)
                }
    }
    return elements.toString()
}
