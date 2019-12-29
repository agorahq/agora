package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.module.Module
import org.hexworks.cobalt.datatypes.Maybe
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DefaultModuleRegistry : ModuleRegistry {

    override val modules: Iterable<Module<out DomainObject>>
        get() = moduleLookup.map { it.value }

    private val moduleLookup = ConcurrentHashMap<KClass<Module<out DomainObject>>, Module<out DomainObject>>()

    override fun <D : DomainObject> findModule(klass: KClass<Module<D>>): Maybe<Module<D>> {
        return Maybe.ofNullable(moduleLookup[klass as KClass<Module<out DomainObject>>] as Module<D>)
    }

    override fun register(module: Module<out DomainObject>) {
        moduleLookup[module::class as KClass<Module<out DomainObject>>] = module
    }
}