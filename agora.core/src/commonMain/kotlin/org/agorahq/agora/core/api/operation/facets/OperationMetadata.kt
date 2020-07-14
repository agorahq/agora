package org.agorahq.agora.core.api.operation.facets

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.Facet
import kotlin.reflect.KClass

data class OperationMetadata<R : Resource, I : Any, O : Any>(
        val resourceClass: KClass<R>,
        val inputClass: KClass<I>,
        val outputClass: KClass<O>,
        val urlClass: KClass<ResourceURL<R>>,
        val route: String
) : Facet {
    override fun matches(other: Facet) = this == other
}
