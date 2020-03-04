package org.agorahq.agora.core.api.extensions

fun <T : Any, R : Any, C : Any> Sequence<T>.mapWith(context: C, transform: C.(T) -> R): Sequence<R> {
    return map { item ->
        with(context) {
            transform(item)
        }
    }
}
