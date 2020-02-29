package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.Module
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass

interface ModuleRegistry {

    val modules: Iterable<Module<out ContentResource>>

    fun <C : ContentResource> findModule(klass: KClass<Module<C>>): Maybe<Module<C>>

    fun register(module: Module<out ContentResource>)
}