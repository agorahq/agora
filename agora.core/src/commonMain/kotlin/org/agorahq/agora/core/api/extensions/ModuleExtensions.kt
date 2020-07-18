@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Attribute

fun SiteMetadata.forEachModuleHavingOperationWithFacet(
        attribute: Attribute, fn: (Pair<Module<out Resource>, AnyOperation>) -> Unit
) {
    modules.flatMap { module ->
        module.findMatchingOperations(attribute).map { module to it }
    }.forEach(fn)
}
