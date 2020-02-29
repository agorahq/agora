package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.Module

val SiteMetadata.modules: Iterable<Module<out ContentResource>>
    get() = moduleRegistry.modules

fun SiteMetadata.parentOf(operation: AnyContentOperation): Module<out ContentResource> {
    return modules.first {
        it.hasOperation(operation)
    }
}