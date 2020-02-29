package org.agorahq.agora.core.module.base

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.ContentListingContext
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.context.PageContext
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<C : Content>(
        operations: Iterable<Operation>,
        override val contentClass: KClass<C>
) : Module<C> {

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

    override fun supportsContent(content: Content) = content::class === contentClass

    override fun supportsContext(context: OperationContext): Boolean {
        return when (context) {
            is PageContext<out Page> -> supportsContent(context.page)
            is ContentListingContext<out Content> -> context.items.firstOrNull()?.let { item ->

            }
            else -> false
        }
    }

    final override fun <T : Operation> findOperation(operationType: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(filterOperations(operationType).firstOrNull())
    }

    final override fun <T : Operation> filterOperations(operationType: KClass<T>): Iterable<T> {
        return operations.filterKeys { operationType.isSuperclassOf(it) }.map { it.value as T }
    }

}