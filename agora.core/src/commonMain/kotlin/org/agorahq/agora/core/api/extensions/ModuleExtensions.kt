@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.OperationType
import org.agorahq.agora.core.api.view.ViewModel

fun <R : Resource, C : OperationContext, T : Any> SiteMetadata.forEachModuleHavingOperationWithType(
        type: OperationType<R, C, T>, fn: (Pair<Module<out Resource, out ViewModel>, Operation<R, C, T>>) -> Unit
) {
    modules.flatMap { module ->
        module.findMatchingOperations(type).map { module to it }
    }.forEach(fn)
}
