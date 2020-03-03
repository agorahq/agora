package org.agorahq.agora.core.api.view

import org.agorahq.agora.core.api.resource.Resource
import kotlin.reflect.KClass

interface ResourceConverter<M : ViewModel, R : Resource> {

    val viewModelClass: KClass<M>
    val resourceClass: KClass<R>

    fun M.toResource(): R

    fun R.toViewModel(): M

}