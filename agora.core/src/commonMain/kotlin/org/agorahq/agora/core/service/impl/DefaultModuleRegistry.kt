package org.agorahq.agora.core.service.impl

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.extensions.AnyModule
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.ServerContext
import org.agorahq.agora.core.service.ModuleRegistry
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class DefaultModuleRegistry<S : ServerContext> : ModuleRegistry<S> {

    override val modules: Iterable<Module<out DomainObject, S>>
        get() = moduleLookup.map { it.value }

    private val moduleLookup = mutableMapOf<KClass<Module<out DomainObject, S>>, Module<out DomainObject, S>>()

    override fun <D : DomainObject> findModule(klass: KClass<Module<D, S>>): Maybe<Module<D, S>> {
        return Maybe.ofNullable(moduleLookup[klass as KClass<Module<out DomainObject, S>>] as Module<D, S>)
    }

    override fun register(module: Module<out DomainObject, S>) {
        moduleLookup[module::class as KClass<Module<out DomainObject, S>>] = module
    }
}