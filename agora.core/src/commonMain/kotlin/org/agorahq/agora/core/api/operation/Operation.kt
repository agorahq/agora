package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor

/**
 * An [Operation] is a procedure which performs some kind of CRUD operation on a [Resource]
 * in a given [OperationContext] and returns a value ([T]).
 *
 * An [Operation] can be **reified** by calling [Operation.createCommand] using the
 * context ([C]) the operation needs.
 *
 * This [Command] then can be executed without parameters which makes it possible to pass
 * [Command]s around in the application without having to worry about their parameters.
 */
interface Operation<R : Resource, C : OperationContext, T : Any> : OperationDescriptor<R, C, T> {

    val descriptor: OperationDescriptor<R, C, T>

    fun C.createCommand(): Command<T>
}
