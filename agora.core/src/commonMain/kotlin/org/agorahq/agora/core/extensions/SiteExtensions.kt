package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.Facet
import org.agorahq.agora.core.module.Module

val Site.modules: Iterable<AnyModule>
    get() = moduleRegistry.modules

fun Site.parentOf(facet: Facet): Module<out DomainObject> {
    return modules.first {
        it.containsFacet(facet)
    }
}