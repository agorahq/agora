@file:Suppress("SpellCheckingInspection")

package org.agorahq.agora.delivery.adapter

import io.ktor.routing.Routing
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation

interface KtorOperationAdapter<R : Resource, I : Any, O : Any> : Operation<R, I, O> {

    val operation: Operation<R, I, O>

    fun Routing.register()

}
