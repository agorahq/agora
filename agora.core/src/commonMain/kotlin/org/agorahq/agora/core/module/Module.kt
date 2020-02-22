package org.agorahq.agora.core.module

import org.agorahq.agora.core.domain.document.DocumentPart
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface Module<E : DocumentPart> {

    val name: String

    fun hasOperation(operationType: KClass<out Operation>): Boolean

    fun hasOperation(operation: Operation): Boolean = hasOperation(operation::class)

    fun <T : Operation> findOperation(operationType: KClass<T>): Maybe<T>

    fun <T : Operation> filterOperations(operationType: KClass<T>): Iterable<T>

}