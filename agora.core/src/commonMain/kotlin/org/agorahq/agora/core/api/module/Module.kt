package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Attribute
import org.agorahq.agora.core.api.operation.Operation
import kotlin.reflect.KClass

/**
 * A [Module] is a collection of [Operation]s which are performed on the same
 * [Resource] of type [R].
 */
interface Module<R : Resource> {

    val name: String
    val resourceClass: KClass<R>

    fun findMatchingOperations(
            attribute: Attribute
    ): Iterable<AnyOperation>

    fun hasMatchingOperation(
            attribute: Attribute
    ): Boolean

    fun supportsResource(resource: Resource): Boolean

}
