@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.Operation

inline fun <reified T : Operation> Module<out Content>.whenHasOperation(noinline fn: (T) -> Unit) {
    findOperation(T::class).map(fn)
}

inline fun <reified T : Operation> SiteMetadata.forEachModuleWithOperation(fn: (Pair<AnyModule, T>) -> Unit) {
    modules.filter { it.hasOperation(T::class) }.map {
        fn(it as Module<Content> to it.findOperation(T::class).get())
    }
}