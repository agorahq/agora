package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.Resource

fun <R : Resource, T : Any> Result<out ResourceContext<R>, out Exception>.tryToExecute(
        operation: Operation<R, ResourceContext<R>, T>
): Result<out T, out Exception> {
    return flatMap { ctx: ResourceContext<R> ->
        with(operation) {
            ctx.reify().execute()
        }
    }
}
