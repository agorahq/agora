package org.agorahq.agora.core.api.module.base

import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationType
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.platform.isSubclassOf
import org.agorahq.agora.core.platform.isSuperclassOf
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<R : Resource, M : ViewModel>(
        operations: Iterable<AnyOperation>,
        override val resourceClass: KClass<R>,
        override val viewModelClass: KClass<M>
) : Module<R, M> {

    private val operations = operations.map {
        it::class to it
    }.toMap().toMutableMap()

    override fun <R : Resource, C : OperationContext, T : OperationType<R, C, U>, U : Any> findOperationsWithType(
            operationType: T
    ): Iterable<Operation<R, C, T, U>> {
        return operations.values.filter {
            it.type == operationType
        }.map { it as Operation<R, C, T, U> }
    }

    override fun <R : Resource, C : OperationContext, T : OperationType<R, C, U>, U : Any> hasOperationWithType(operationType: T): Boolean {
        return operations.values.any { it.type == operationType }
    }

    override fun supportsResource(resource: Resource) = isSubclassOf(resource::class, resourceClass)

    override fun supportsViewModel(viewModel: ViewModel) = isSuperclassOf(viewModel::class, viewModelClass)

    override fun supportsContext(context: OperationContext): Boolean {
        return when (context) {
            is ViewModelContext<*> -> supportsViewModel(context.viewModel)
            else -> false
        }
    }

}