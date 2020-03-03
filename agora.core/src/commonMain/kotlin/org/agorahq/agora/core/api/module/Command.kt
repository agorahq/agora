package org.agorahq.agora.core.api.module

import org.agorahq.agora.core.api.data.Result

interface Command<T : Any> {

    fun execute(): Result<out T, out Exception>
}