@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.view.ViewModel

inline fun <reified T : AnyContentOperation> Module<out Resource, out ViewModel>.whenHasOperation(noinline fn: (T) -> Unit) {
    findOperation(T::class).map(fn)
}

inline fun <reified T : AnyContentOperation> SiteMetadata.forEachModuleWithOperation(fn: (Pair<AnyModule, T>) -> Unit) {
    modules.filter { it.hasOperation(T::class) }.map {
        fn(it as Module<Resource, ViewModel> to it.findOperation(T::class).get())
    }
}