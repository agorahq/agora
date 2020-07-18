package org.agorahq.agora.core.api.operation.facets

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.Attribute
import org.agorahq.agora.core.platform.isSuperclassOf
import kotlin.reflect.KClass

data class OperationMetadata<R : Resource, I : Any, O : Any>(
        val resourceClass: KClass<R>,
        val inputClass: KClass<I>,
        val outputClass: KClass<O>,
        val urlClass: KClass<ResourceURL<R>>,
        val route: String
) : Attribute {

    override fun matches(other: Attribute) = if (other is OperationMetadata<out Resource, out Any, out Any>) {
        isSuperclassOf(resourceClass, other.resourceClass) &&
                isSuperclassOf(inputClass, other.inputClass) &&
                isSuperclassOf(outputClass, other.outputClass) &&
                isSuperclassOf(urlClass, other.urlClass)
    } else false

}
