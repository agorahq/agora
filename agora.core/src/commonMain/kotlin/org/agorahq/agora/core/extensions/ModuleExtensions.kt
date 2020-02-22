package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.Operation

inline fun <reified T : Operation> AnyModule.whenHasOperation(noinline fn: (T) -> Unit) {
    findOperation(T::class).map(fn)
}

inline fun <reified T : Operation> Site.forEachModuleWithOperation(fn: (Pair<AnyModule, T>) -> Unit) {
    modules.filter { it.hasOperation(T::class) }.map {
        fn(it to it.findOperation(T::class).get())
    }
}