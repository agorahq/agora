package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module

val SiteMetadata.modules: Iterable<Module<out Resource>>
    get() = moduleRegistry.modules

