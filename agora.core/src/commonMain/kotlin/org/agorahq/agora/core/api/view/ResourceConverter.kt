package org.agorahq.agora.core.api.view

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.data.Resource
import kotlin.reflect.KClass

interface ResourceConverter<M : ViewModel, R : Resource> {

    val viewModelClass: KClass<M>
    val resourceClass: KClass<R>

    fun M.toResource(): R

    fun R.toViewModel(context: OperationContext): M

}
