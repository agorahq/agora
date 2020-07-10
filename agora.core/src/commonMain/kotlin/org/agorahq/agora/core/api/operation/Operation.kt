package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext

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

    fun C.fetchData(): ElementSource<R>

    fun C.createCommand(data: ElementSource<R>): Command<T>

}
