@file:Suppress("SpellCheckingInspection")

package org.agorahq.agora.delivery.adapter

import io.ktor.routing.Routing
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext

interface KtorOperationAdapter<R : Resource, C : OperationContext, T : Any> : Operation<R, C, T> {

    val operation: Operation<R, C, T>

    fun  Routing.register()

}
