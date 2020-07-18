package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.Module

interface ModuleRegistry {

    val modules: Iterable<Module<out Resource>>

    fun register(module: Module<out Resource>)

}
