package org.agorahq.agora.core.internal.service

import kotlinx.collections.immutable.persistentMapOf
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.service.ModuleRegistry
import org.agorahq.agora.core.api.view.ViewModel
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DefaultModuleRegistry : ModuleRegistry {

    override val modules: Iterable<Module<out Resource, out ViewModel>>
        get() = moduleLookup.map { it.value }

    private var moduleLookup = persistentMapOf<KClass<Module<out Resource, out ViewModel>>, Module<out Resource, out ViewModel>>()

//    override fun <D : Resource> findModule(klass: KClass<Module<D>>): Maybe<Module<D>> {
//        return Maybe.ofNullable(moduleLookup[klass as KClass<Module<out Resource>>] as Module<D>)
//    }

    override fun register(module: Module<out Resource, out ViewModel>) {
        moduleLookup = moduleLookup.put(module::class as KClass<Module<out Resource, out ViewModel>>, module)
    }
}