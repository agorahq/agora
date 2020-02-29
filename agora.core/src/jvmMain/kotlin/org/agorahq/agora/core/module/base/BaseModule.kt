package org.agorahq.agora.core.module.base

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.Resource
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<C : ContentResource>(
        operations: Iterable<AnyContentOperation>,
        override val contentClass: KClass<C>
) : Module<C> {

    private val operations = operations.map {
        it::class to it
    }.toMap().toMutableMap()

    final override fun hasOperation(operation: AnyContentOperation): Boolean {
        return operations.any { it.value === operation }
    }

    final override fun hasOperation(operationType: KClass<out AnyContentOperation>): Boolean {
        return operations.filterKeys {
            operationType.isSuperclassOf(it)
        }.isNotEmpty()
    }

    override fun supportsResource(resource: Resource) = resource::class === contentClass

    override fun supportsContext(context: OperationContext): Boolean {
        return when (context) {
            is ResourceContext<*> -> supportsResource(context.resource)
            else -> false
        }
    }

    final override fun <T : AnyContentOperation> findOperation(operationType: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(filterOperations(operationType).firstOrNull())
    }

    final override fun <T : AnyContentOperation> filterOperations(operationType: KClass<T>): Iterable<T> {
        return operations.filterKeys { operationType.isSuperclassOf(it) }.map { it.value as T }
    }

}