package org.agorahq.agora.core.api.module.base

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Facet
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.platform.isSubclassOf
import org.agorahq.agora.core.platform.isSuperclassOf
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class BaseModule<R : Resource, M : ViewModel>(
        operations: Iterable<Operation<out Resource, out Any, out Any>>,
        override val resourceClass: KClass<R>,
        override val viewModelClass: KClass<M>
) : Module<R, M> {

    private val operations = (operations as Iterable<AnyOperation>).map {
        it::class to it
    }.toMap().toMutableMap()

    override fun findMatchingOperations(
            facet: Facet
    ): Iterable<AnyOperation> {
        return operations.values.filter { op ->
            op.facets.any { it.matches(facet) }
        }.map { it }
    }

    override fun hasMatchingOperation(
            facet: Facet
    ): Boolean {
        return operations.values.any { op -> op.facets.any { it.matches(facet) } }
    }

    override fun supportsResource(resource: Resource) = isSubclassOf(resource::class, resourceClass)

    override fun supportsViewModel(viewModel: ViewModel) = isSuperclassOf(viewModel::class, viewModelClass)

}
