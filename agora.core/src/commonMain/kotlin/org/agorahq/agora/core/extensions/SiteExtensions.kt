package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.document.Content
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.Operation

val Site.modules: Iterable<AnyModule>
    get() = moduleRegistry.modules

fun Site.parentOf(operation: Operation): Module<out Content> {
    return modules.first {
        it.hasOperation(operation)
    }
}