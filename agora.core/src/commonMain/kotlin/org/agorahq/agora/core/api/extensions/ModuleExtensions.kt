@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationType
import org.agorahq.agora.core.api.view.ViewModel

fun <R : Resource, C : OperationContext, T : OperationType<R, C, U>, U : Any> SiteMetadata.forEachModuleHavingOperationWithType(
        type: T, fn: (Pair<Module<out Resource, out ViewModel>, Operation<R, C, T, U>>) -> Unit
) {
    modules.flatMap { module ->
        module.findOperationsWithType(type).map { module to it }
    }.forEach(fn)
}