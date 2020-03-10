package org.agorahq.agora.core.api.data

sealed class Validated<T> {

    data class  Valid<T>(val value: T) : Validated<T>()
}