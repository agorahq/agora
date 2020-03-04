@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.view

import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.internal.view.DefaultConverterService
import kotlin.reflect.KClass

interface ConverterService {

    fun register(converter: ResourceConverter<ViewModel, Resource>)

    fun findViewModelClassFor(klass: KClass<out Resource>): KClass<out ViewModel>

    fun <R : Resource> convertToResource(view: ViewModel): Result<out R, out Exception>

    fun <V : ViewModel> convertToView(resource: Resource, context: OperationContext): Result<out V, out Exception>

    companion object {

        fun create(converters: Iterable<ResourceConverter<out ViewModel, out Resource>> = listOf()): ConverterService {
            return DefaultConverterService(converters as Iterable<ResourceConverter<ViewModel, Resource>>)
        }

    }
}