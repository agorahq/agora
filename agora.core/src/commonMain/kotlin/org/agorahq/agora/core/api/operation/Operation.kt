package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext

/**
 * An [Operation] is a function which alters a [Resource] in some way
 * in a given [OperationContext] and returns a value (of type [O]).
 *
 * An [Operation] can be **reified** by calling [Operation.createCommand] using the
 * context and the inputs ([I]) the operation needs.
 *
 * This [Command] then can be executed without parameters which makes it possible to pass
 * [Command]s around in the application without having to worry about their parameters.
 */
interface Operation<R : Resource, I : Any, O : Any> : OperationDescriptor<R, I, O> {

    /**
     * Returns the resource (s) that will be used for the resulting [Command].
     */
    fun fetchResource(context: OperationContext<out I>): ElementSource<R>

    /**
     * Creates a standalone [Command] from the given [context] and [data] that can be
     * executed.
     */
    fun createCommand(context: OperationContext<out I>, data: ElementSource<R>): Command<O>

}
