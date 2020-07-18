package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.operation.facets.OperationMetadata
import kotlin.reflect.KClass

/**
 * An [OperationDescriptor] contains metadata which is necessary to perform
 * an [Operation]. each [OperationDescriptor] must have an unique [name].
 *
 * [OperationDescriptor]s can have arbitrary metadata in them (called [Attribute]s),
 * but each [OperationDescriptor] must have an [OperationMetadata] [Attribute] which
 * contains the mandatory information.
 * @param R the [Resource] this operation works on
 * @param I the input of the [Operation]
 * @param O the output of the [Operation]
 */
interface OperationDescriptor<R : Resource, I : Any, O : Any> {

    val name: String
    val attributes: Attributes<R, I, O>

    val resourceClass: KClass<R>
        get() = attributes.resourceClass
    val inputClass: KClass<I>
        get() = attributes.inputClass
    val outputClass: KClass<O>
        get() = attributes.outputClass
    val urlClass: KClass<ResourceURL<R>>
        get() = attributes.urlClass
    val route: String
        get() = attributes.route

}
