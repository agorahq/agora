package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext

/**
 * An [Operation] is a function which alters a [Resource] in some way
 * in a given [OperationContext] and returns a value (of type [T]).
 *
 * An [Operation] can be **reified** by calling [Operation.createCommand] using the
 * context ([C]) the operation needs.
 *
 * This [Command] then can be executed without parameters which makes it possible to pass
 * [Command]s around in the application without having to worry about their parameters.
 */
interface Operation<R : Resource, C : OperationContext, T : Any> : OperationDescriptor<R, C, T> {

    /**
     * Returns the resource (s) that will be used for the resulting [Command].
     */
    fun fetchResource(context: C): ElementSource<R>

    /**
     * Creates a standalone [Command] from the given [context] and [data] that can be
     * executed.
     */
    fun createCommand(context: C, data: ElementSource<R>): Command<T>

}
