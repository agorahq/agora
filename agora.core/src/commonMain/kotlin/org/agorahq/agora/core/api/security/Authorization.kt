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
     * Tells whether the given [operationDescriptor] can be executed in the given [context]
     */
    fun <C : OperationContext> canExecute(
            context: C,
            operationDescriptor: AnyOperationDescriptor
    ): Boolean

    /**
     * Tries to authorize the given [operation] within the given [context]. If successful a [Command]
     * is returned which can be called to execute the - now authorized - [operation].
     * Otherwise an [AuthorizationException] is returned with a detailed error message.
     */
    fun <R : Resource, C : OperationContext, T : Any> authorize(
            context: C,
            operation: Operation<R, C, T>
    ): Result<out Command<T>, out AuthorizationException>

}
