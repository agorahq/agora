package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.module.context.OperationContext
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface Module<C : Content> {

    val name: String
    val contentClass: KClass<C>

    fun hasOperation(operationType: KClass<out Operation>): Boolean

    fun hasOperation(operation: Operation): Boolean = hasOperation(operation::class)

    fun supportsContent(content: Content): Boolean

    fun supportsContext(context: OperationContext): Boolean

    fun <T : Operation> findOperation(operationType: KClass<T>): Maybe<T>

    fun <T : Operation> filterOperations(operationType: KClass<T>): Iterable<T>

}
