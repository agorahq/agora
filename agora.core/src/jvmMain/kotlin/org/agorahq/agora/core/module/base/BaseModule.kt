package org.agorahq.agora.core.module.base

import org.agorahq.agora.core.domain.document.Content
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.Operation
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<E : Content>(
        operations: Iterable<Operation>
) : Module<E> {

    private val operations = operations.map {
        it::class to it
    }.toMap().toMutableMap()

    final override fun hasOperation(operation: Operation): Boolean {
        return operations.any { it.value === operation }
    }

    final override fun hasOperation(operationType: KClass<out Operation>): Boolean {
        return operations.filterKeys {
            operationType.isSuperclassOf(it)
        }.isNotEmpty()
    }

    final override fun <T : Operation> findOperation(operationType: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(filterOperations(operationType).firstOrNull())
    }

    final override fun <T : Operation> filterOperations(operationType: KClass<T>): Iterable<T> {
        return operations.filterKeys { operationType.isSuperclassOf(it) }.map { it.value as T }
    }

}