package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.Facet

inline fun <reified T : Facet> AnyModule.whenHasFacet(noinline fn: (T) -> Unit) {
    findFacet(T::class).map(fn)
}

inline fun <reified T : Facet> Site.forEachModuleWithFacet(fn: (Pair<AnyModule, T>) -> Unit) {
    modules.filter { it.hasFacet(T::class) }.map {
        fn(it to it.findFacet(T::class).get())
    }
}