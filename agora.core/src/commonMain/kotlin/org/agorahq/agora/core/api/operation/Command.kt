package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Result

interface Command<T : Any> {

    fun execute(): Result<out T, out Exception>
}