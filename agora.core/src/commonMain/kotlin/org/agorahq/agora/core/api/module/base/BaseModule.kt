package org.agorahq.agora.core.api.module.base

import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.context.ViewContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.platform.isSubclassOf
import org.agorahq.agora.core.platform.isSuperclassOf
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<R : Resource, M : ViewModel>(
        operations: Iterable<AnyContentOperation>,
        override val resourceClass: KClass<R>,
        override val viewModelClass: KClass<M>
) : Module<R, M> {

    private val operations = operations.map {
        it::class to it
    }.toMap().toMutableMap()

    final override fun hasOperation(operation: AnyContentOperation): Boolean {
        return operations.any { it.value === operation }
    }

    final override fun hasOperation(operationType: KClass<out AnyContentOperation>): Boolean {
        return operations.filterKeys {
            isSuperclassOf(operationType, it)
        }.isNotEmpty()
    }

    override fun supportsResource(resource: Resource) = isSubclassOf(resource::class, resourceClass)

    override fun supportsViewModel(viewModel: ViewModel) = isSuperclassOf(viewModel::class, viewModelClass)

    override fun supportsContext(context: OperationContext): Boolean {
        return when (context) {
            is ViewContext<*> -> supportsViewModel(context.viewModel)
            else -> false
        }
    }

    final override fun <T : AnyContentOperation> findOperation(operationType: KClass<T>): Maybe<T> {
        return Maybe.ofNullable(filterOperations(operationType).firstOrNull())
    }

    final override fun <T : AnyContentOperation> filterOperations(operationType: KClass<T>): Iterable<T> {
        return operations.filterKeys { isSuperclassOf(operationType, it) }.map { it.value as T }
    }

}