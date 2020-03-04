package org.agorahq.agora.core.api.data

sealed class Result<T : Any, F : Exception> {

    data class Success<S : Any>(val result: S) : Result<S, Nothing>()
    data class Failure<F : Exception>(val exception: F) : Result<Nothing, F>()

    fun get(): T {
        return when (this) {
            is Success -> result
            is Failure -> throw exception
        }
    }

    fun <R : Any> map(fn: (T) -> R): Result<out R, out Exception> {
        return when (this) {
            is Success -> Success(fn(result))
            is Failure -> this
        }
    }

    fun <R : Any> flatMap(fn: (T) -> Result<out R, out Exception>): Result<out R, out Exception> {
        return when (this) {
            is Success -> fn(result)
            is Failure -> this
        }
    }

    companion object {

        fun <T : Any> create(obj: T?, exceptionProvider: () -> Exception): Result<out T, out Exception> {
            return obj?.let {
                Success(it)
            } ?: Failure(exceptionProvider())
        }
    }
}