package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Result

/**
 * A [Command] is a reified [Operation]. It contains all the necessary information
 * to be executed without the need for any further parameters.
 * @param T the type of the result returned from executing this [Command].
 */
interface Command<T : Any> {

    fun execute(): Result<out T, out Exception>
}
