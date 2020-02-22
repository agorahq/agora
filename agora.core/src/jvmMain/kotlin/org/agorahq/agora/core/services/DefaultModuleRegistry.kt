package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.DocumentPart
import org.agorahq.agora.core.module.Module
import org.hexworks.cobalt.datatypes.Maybe
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DefaultModuleRegistry : ModuleRegistry {

    override val modules: Iterable<Module<out DocumentPart>>
        get() = moduleLookup.map { it.value }

    private val moduleLookup = ConcurrentHashMap<KClass<Module<out DocumentPart>>, Module<out DocumentPart>>()

    override fun <D : DocumentPart> findModule(klass: KClass<Module<D>>): Maybe<Module<D>> {
        return Maybe.ofNullable(moduleLookup[klass as KClass<Module<out DocumentPart>>] as Module<D>)
    }

    override fun register(module: Module<out DocumentPart>) {
        moduleLookup[module::class as KClass<Module<out DocumentPart>>] = module
    }
}