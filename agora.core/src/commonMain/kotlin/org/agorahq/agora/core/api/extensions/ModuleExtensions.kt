@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.Attribute
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.facets.OperationMetadata
import kotlin.reflect.KClass

inline fun <reified R : Resource, reified I : Any, reified O : Any> SiteMetadata.findMatchingOperations(
        vararg attributes: Attribute
): Iterable<Operation<R, I, O>> {
    return moduleRegistry.modules.flatMap { it.findMatchingOperations<R, I, O>(*attributes) }
}

inline fun <reified R : Resource, reified I : Any, reified O : Any> Module<out Resource>.findMatchingOperations(
        vararg attributes: Attribute
): Iterable<Operation<R, I, O>> {
    val toCheck = attributes.toList() + OperationMetadata(
            resourceClass = R::class,
            inputClass = I::class,
            outputClass = O::class,
            urlClass = ResourceURL::class as KClass<ResourceURL<R>>,
            route = "")
    return operations.filter { op ->
        toCheck.map { filterAttribute ->
            op.attributes.firstOrNull { it::class == filterAttribute::class }?.let { opAttribute ->
                filterAttribute.matches(opAttribute)
            } ?: false
        }.fold(true, Boolean::and)
    } as Iterable<Operation<R, I, O>>
}
