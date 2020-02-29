package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.api.extensions.AnyOperation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface Module<C : ContentResource> {

    val name: String
    val contentClass: KClass<C>

    fun hasOperation(operationType: KClass<out AnyContentOperation>): Boolean

    fun hasOperation(operation: AnyContentOperation): Boolean = hasOperation(operation::class)

    fun supportsResource(resource: Resource): Boolean

    fun supportsContext(context: OperationContext): Boolean

    fun <T : AnyContentOperation> findOperation(operationType: KClass<T>): Maybe<T>

    fun <T : AnyContentOperation> filterOperations(operationType: KClass<T>): Iterable<T>

}
