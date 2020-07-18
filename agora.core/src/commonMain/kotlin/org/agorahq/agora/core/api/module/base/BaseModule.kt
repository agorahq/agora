package org.agorahq.agora.core.api.module.base

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Attribute
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.platform.isSubclassOf
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<R : Resource>(
        operations: Iterable<Operation<out Resource, out Any, out Any>>,
        override val resourceClass: KClass<R>
) : Module<R> {

    private val operations = (operations as Iterable<AnyOperation>).map {
        it::class to it
    }.toMap().toMutableMap()

    override fun findMatchingOperations(
            attribute: Attribute
    ): Iterable<AnyOperation> {
        return operations.values.filter { op ->
            op.attributes.any { it.matches(attribute) }
        }.map { it }
    }

    override fun hasMatchingOperation(
            attribute: Attribute
    ): Boolean {
        return operations.values.any { op -> op.attributes.any { it.matches(attribute) } }
    }

    override fun supportsResource(resource: Resource) = isSubclassOf(resource::class, resourceClass)

}
