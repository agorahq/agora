package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.module.Module
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface ModuleRegistry {

    val modules: Iterable<Module<out Content>>

    fun <C : Content> findModule(klass: KClass<Module<C>>): Maybe<Module<C>>

    fun register(module: Module<out Content>)
}