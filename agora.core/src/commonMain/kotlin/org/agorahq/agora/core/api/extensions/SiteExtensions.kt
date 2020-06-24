package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.view.ViewModel

val SiteMetadata.modules: Iterable<Module<out Resource, out ViewModel>>
    get() = moduleRegistry.modules

