package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.ServerContext
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface ModuleRegistry<S : ServerContext> {

    val modules: Iterable<Module<out DomainObject, S>>

    fun <D : DomainObject> findModule(klass: KClass<Module<D, S>>): Maybe<Module<D, S>>

    fun register(module: Module<out DomainObject, S>)
}