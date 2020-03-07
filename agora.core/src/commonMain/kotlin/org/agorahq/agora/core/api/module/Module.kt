package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationType
import org.agorahq.agora.core.api.view.ViewModel
import kotlin.reflect.KClass

interface Module<R : Resource, M : ViewModel> {

    val name: String
    val resourceClass: KClass<R>
    val viewModelClass: KClass<M>

    fun <R : Resource, C : OperationContext, T : Any> findMatchingOperations(
            type: OperationType<R, C, T>
    ): Iterable<Operation<R, C, T>>

    fun <R : Resource, C : OperationContext, T : Any> hasMatchingOperation(
            type: OperationType<R, C, T>
    ): Boolean

    fun supportsResource(resource: Resource): Boolean

    fun supportsViewModel(viewModel: ViewModel): Boolean

    fun supportsContext(context: OperationContext): Boolean

}
