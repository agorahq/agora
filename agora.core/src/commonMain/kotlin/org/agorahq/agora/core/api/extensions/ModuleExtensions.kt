@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Facet
import org.agorahq.agora.core.api.view.ViewModel

fun SiteMetadata.forEachModuleHavingOperationWithFacet(
        facet: Facet, fn: (Pair<Module<out Resource, out ViewModel>, AnyOperation>) -> Unit
) {
    modules.flatMap { module ->
        module.findMatchingOperations(facet).map { module to it }
    }.forEach(fn)
}
