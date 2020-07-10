package org.agorahq.agora.core.api.data

/**
 * Represents the result of an operation. It can either be the return value
 * of an operation of type [T] or an [Exception] of type [F].
 */
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

    fun orElse(other: T): Result<out T, out F> {
        return when (this) {
            is Success -> Success(other)
            is Failure -> throw exception
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
