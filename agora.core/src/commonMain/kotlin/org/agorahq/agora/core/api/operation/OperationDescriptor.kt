package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.facets.OperationMetadata
import org.agorahq.agora.core.internal.security.DefaultOperationDescriptor
import kotlin.reflect.KClass

/**
 * An [OperationDescriptor] contains metadata which is necessary to perform
 * an [Operation]. each [OperationDescriptor] must have an unique [name].
 *
 * [OperationDescriptor]s can have arbitrary metadata in them (called [Facet]s),
 * but each [OperationDescriptor] must have an [OperationMetadata] [Facet] which
 * contains the mandatory information.
 * @param R the [Resource] this operation works on
 * @param I the input of the [Operation]
 * @param O the output of the [Operation]
 */
interface OperationDescriptor<R : Resource, I : Any, O : Any> {

    val name: String
    val facets: Facets<R, I, O>

    val resourceClass: KClass<R>
        get() = facets.resourceClass
    val parametersClass: KClass<I>
        get() = facets.parametersClass
    val outputClass: KClass<O>
        get() = facets.outputClass
    val urlClass: KClass<ResourceURL<R>>
        get() = facets.urlClass
    val route: String
        get() = facets.route

    @Suppress("UNCHECKED_CAST")
    companion object {

        inline fun <reified R : Resource, reified I : Any, reified O : Any> create(
                name: String,
                route: String,
                urlClass: KClass<out ResourceURL<R>>,
                facets: Iterable<Facet>
        ): OperationDescriptor<R, I, O> = DefaultOperationDescriptor(
                name = name,
                facets = Facets(OperationMetadata(
                        resourceClass = R::class,
                        inputClass = I::class,
                        outputClass = O::class,
                        urlClass = urlClass as KClass<ResourceURL<R>>,
                        route = route
                ), facets.toList())
        )

        fun <R : Resource, I : Any, O : Any> toString(
                descriptor: OperationDescriptor<R, I, O>
        ) = descriptor.name

    }


}
