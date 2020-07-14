@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.exception.AuthorizationException
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.builder.AuthorizationBuilder

/**
 * [Authorization] represents the authorization settings for a whole application including
 * [Role]s, [Group]s, [Permission]s and policies. Use [AuthorizationBuilder] to create
 * a new [Authorization] object.
 */
interface Authorization {

    /**
     * Tells whether the given [operation] can be executed in the given [context]
     */
    fun canExecute(
            context: OperationContext<Any>,
            operation: AnyOperationDescriptor
    ): Boolean

    /**
     * Tries to authorize the given [operation] within the given [context]. If successful a [Command]
     * is returned which can be called to execute the - now authorized - [operation].
     * Otherwise an [AuthorizationException] is returned with a detailed error message.
     */
    fun <O : Any> authorizeAny(
            context: OperationContext<out Any>,
            operation: Operation<Resource, Any, O>
    ): Result<out Command<O>, out AuthorizationException>
}

/**
 * Tries to authorize the given [operation] within the given [context]. If successful a [Command]
 * is returned which can be called to execute the - now authorized - [operation].
 * Otherwise an [AuthorizationException] is returned with a detailed error message.
 */
fun <R: Resource, I: Any, O : Any> Authorization.authorize(
        context: OperationContext<I>,
        operation: Operation<R, I, O>
): Result<out Command<O>, out AuthorizationException> {
    return authorizeAny(context as OperationContext<out Any>, operation as Operation<Resource, Any, O>)
}

