package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.facets.OperationMetadata
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class Attributes<R : Resource, P : Any, O : Any>(
        private val operationMetadata: OperationMetadata<R, P, O>,
        rest: Iterable<Attribute>
) : Iterable<Attribute> {

    val resourceClass: KClass<R>
        get() = operationMetadata.resourceClass
    val inputClass: KClass<P>
        get() = operationMetadata.inputClass
    val outputClass: KClass<O>
        get() = operationMetadata.outputClass
    val urlClass: KClass<ResourceURL<R>>
        get() = operationMetadata.urlClass
    val route: String
        get() = operationMetadata.route

    private val facets = listOf<Attribute>(operationMetadata) + rest.toList()

    override fun iterator() = facets.iterator()

    companion object {

        inline fun <reified R : Resource, reified I : Any, reified O : Any> create(
                route: String,
                urlClass: KClass<out ResourceURL<R>>,
                additionalAttributes: Iterable<Attribute>
        ) = Attributes(OperationMetadata(
                resourceClass = R::class,
                inputClass = I::class,
                outputClass = O::class,
                urlClass = urlClass as KClass<ResourceURL<R>>,
                route = route
        ), additionalAttributes.toList())
    }
}
