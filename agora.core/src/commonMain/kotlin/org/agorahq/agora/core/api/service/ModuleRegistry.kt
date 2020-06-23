package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.view.ViewModel

interface ModuleRegistry {

    val modules: Iterable<Module<out Resource, out ViewModel>>

//    fun <C : ContentResource> findModule(klass: KClass<Module<C>>): Maybe<Module<C>>

    fun register(module: Module<out Resource, out ViewModel>)

}
