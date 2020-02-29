package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.Operation

val SiteMetadata.modules: Iterable<Module<out Content>>
    get() = moduleRegistry.modules

fun SiteMetadata.parentOf(operation: Operation): Module<out Content> {
    return modules.first {
        it.hasOperation(operation)
    }
}