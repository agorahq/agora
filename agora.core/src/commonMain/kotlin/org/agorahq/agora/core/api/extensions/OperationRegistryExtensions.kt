@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.Attribute
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.facets.OperationMetadata
import org.agorahq.agora.core.api.service.OperationRegistry
import kotlin.reflect.KClass


inline fun <reified R : Resource, reified I : Any, reified O : Any> OperationRegistry.findMatchingOperations(
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

inline fun <reified A : Attribute> OperationRegistry.findOperationsWithAttribute():
        Iterable<Pair<Operation<out Resource, out Any, out Any>, List<A>>> {
    return operations.map {
        it to it.filterAttributes<A>()
    }.filter { it.second.isNotEmpty() }
}
