package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.module.Facet

inline fun <reified T : Facet> AnyModule.whenHasFacet(noinline fn: (T) -> Unit) {
    findFacet(T::class).map(fn)
}

inline fun <reified T : Facet> Iterable<AnyModule>.filterForFacet(): Iterable<Pair<AnyModule, T>> {
    return filter { it.hasFacet(T::class) }.map {
        it to it.findFacet(T::class).get()
    }
}