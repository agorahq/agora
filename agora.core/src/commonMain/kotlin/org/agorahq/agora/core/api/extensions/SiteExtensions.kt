package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.view.ViewModel

val SiteMetadata.modules: Iterable<Module<out Resource, out ViewModel>>
    get() = moduleRegistry.modules

//fun SiteMetadata.parentOf(operation: AnyContentOperation): Module<out Resource, ViewModel> {
//    return modules.first {
//        it.hasOperation(operation)
//    }
//}