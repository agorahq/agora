package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.module.Command

fun <T : Any> (() -> T).toCommand(): Command<T> = object : Command<T> {

    override fun execute(): Result<out T, out Exception> {
        return try {
            Result.Success(invoke())
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

}
