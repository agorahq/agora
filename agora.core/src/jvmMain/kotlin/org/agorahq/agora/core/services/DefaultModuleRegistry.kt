package org.agorahq.agora.core.services

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.services.ModuleRegistry
import org.hexworks.cobalt.datatypes.Maybe
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DefaultModuleRegistry : ModuleRegistry {

    override val modules: Iterable<Module<out ContentResource>>
        get() = moduleLookup.map { it.value }

    private val moduleLookup = ConcurrentHashMap<KClass<Module<out ContentResource>>, Module<out ContentResource>>()

    override fun <D : ContentResource> findModule(klass: KClass<Module<D>>): Maybe<Module<D>> {
        return Maybe.ofNullable(moduleLookup[klass as KClass<Module<out ContentResource>>] as Module<D>)
    }

    override fun register(module: Module<out ContentResource>) {
        moduleLookup[module::class as KClass<Module<out ContentResource>>] = module
    }
}