package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.module.Module
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface ModuleRegistry {

    val modules: Iterable<Module<out DomainObject>>

    fun <D : DomainObject> findModule(klass: KClass<Module<D>>): Maybe<Module<D>>

    fun register(module: Module<out DomainObject>)
}