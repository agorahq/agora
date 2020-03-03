package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.view.ViewModel
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface Module<R : Resource, M : ViewModel> {

    val name: String
    val resourceClass: KClass<R>
    val viewModelClass: KClass<M>

    fun hasOperation(operationType: KClass<out AnyContentOperation>): Boolean

    fun hasOperation(operation: AnyContentOperation): Boolean = hasOperation(operation::class)

    fun supportsResource(resource: Resource): Boolean

    fun supportsViewModel(viewModel: ViewModel): Boolean

    fun supportsContext(context: OperationContext): Boolean

    fun <T : AnyContentOperation> findOperation(operationType: KClass<T>): Maybe<T>

    fun <T : AnyContentOperation> filterOperations(operationType: KClass<T>): Iterable<T>



}
