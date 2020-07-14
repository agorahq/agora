package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.facets.OperationMetadata
import kotlin.reflect.KClass

class Facets<R : Resource, P : Any, O : Any>(
        private val operationMetadata: OperationMetadata<R, P, O>,
        rest: Iterable<Facet>
) : Iterable<Facet> {

    val resourceClass: KClass<R>
        get() = operationMetadata.resourceClass
    val parametersClass: KClass<P>
        get() = operationMetadata.inputClass
    val outputClass: KClass<O>
        get() = operationMetadata.outputClass
    val urlClass: KClass<ResourceURL<R>>
        get() = operationMetadata.urlClass
    val route: String
        get() = operationMetadata.route

    private val facets = listOf<Facet>(operationMetadata) + rest.toList()

    override fun iterator() = facets.iterator()
}
