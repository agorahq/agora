package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.Content
import org.agorahq.agora.core.module.Module
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface ModuleRegistry {

    val modules: Iterable<Module<out Content>>

    fun <E : Content> findModule(klass: KClass<Module<E>>): Maybe<Module<E>>

    fun register(module: Module<out Content>)
}