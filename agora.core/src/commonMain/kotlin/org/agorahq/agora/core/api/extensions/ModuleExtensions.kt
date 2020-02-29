@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.Module

inline fun <reified T : AnyContentOperation> Module<out ContentResource>.whenHasOperation(noinline fn: (T) -> Unit) {
    findOperation(T::class).map(fn)
}

inline fun <reified T : AnyContentOperation> SiteMetadata.forEachModuleWithOperation(fn: (Pair<AnyModule, T>) -> Unit) {
    modules.filter { it.hasOperation(T::class) }.map {
        fn(it as Module<ContentResource> to it.findOperation(T::class).get())
    }
}