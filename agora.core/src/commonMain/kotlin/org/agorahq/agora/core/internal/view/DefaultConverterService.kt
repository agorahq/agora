package org.agorahq.agora.core.internal.view

import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.data.Result.Failure
import org.agorahq.agora.core.api.exception.MissingConverterException
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.core.api.view.ResourceConverter
import org.agorahq.agora.core.api.view.ViewModel
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST", "ThrowableNotThrown")
class DefaultConverterService(
        converters: Iterable<ResourceConverter<ViewModel, Resource>>
) : ConverterService {

    private val viewModelLookup = converters.map { it.viewModelClass to it }.toMap().toMutableMap()

    private val resourceLookup = converters.map { it.resourceClass to it }.toMap().toMutableMap()

    override fun register(converter: ResourceConverter<ViewModel, Resource>) {
        viewModelLookup[converter.viewModelClass] = converter
        resourceLookup[converter.resourceClass] = converter
    }

    override fun <R : Resource, V : ViewModel> findViewModelClassFor(klass: KClass<R>): KClass<V> {
        return resourceLookup[klass as KClass<Resource>]?.viewModelClass as? KClass<V> ?: error("Can't find view model class for $klass.")
    }

    override fun <R : Resource> convertToResource(view: ViewModel): Result<out R, out Exception> {
        return viewModelLookup[view::class as KClass<ViewModel>]?.let { converter ->
            try {
                Result.Success(with(converter) { view.toResource() } as R)
            } catch (e: Exception) {
                Failure(e)
            }
        } ?: Failure(MissingConverterException(view))
    }

    override fun <V : ViewModel> convertToView(resource: Resource, context: OperationContext<out Any>): Result<out V, out Exception> {
        return resourceLookup[resource::class as KClass<Resource>]?.let { converter ->
            try {
                Result.Success(with(converter) { resource.toViewModel(context) } as V)
            } catch (e: Exception) {
                Failure(e)
            }
        } ?: Failure(MissingConverterException(resource))
    }
}
