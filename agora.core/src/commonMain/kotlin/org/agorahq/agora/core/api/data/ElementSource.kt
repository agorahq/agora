package org.agorahq.agora.core.api.data

import org.hexworks.cobalt.datatypes.Maybe

sealed class ElementSource<out T : Any> {

    abstract fun asSequence(): Sequence<T>

    abstract fun asSingle(): T

    abstract fun filter(fn: (element: T) -> Boolean): ElementSource<T>

    companion object {

        fun <T : Any> fromMaybe(maybe: Maybe<T>): ElementSource<T> = maybe.map {
            fromElement(it)
        }.orElse(EmptyElementSource)

        fun <T : Any> fromElement(element: T): ElementSource<T> = SingleElementSource(element)

        fun <T : Any> fromIterable(elements: Iterable<T>) = MultipleElementSource(elements)

        fun <T : Any> fromSequence(elements: Sequence<T>) = MultipleElementSource(elements.asIterable())
    }
}

object EmptyElementSource : ElementSource<Nothing>() {

    override fun asSequence(): Sequence<Nothing> = sequence { }

    override fun asSingle(): Nothing = throw NoSuchElementException("This element source is empty.")

    override fun filter(fn: (element: Nothing) -> Boolean) = this

}

data class SingleElementSource<T : Any>(val element: T) : ElementSource<T>() {

    override fun asSequence() = sequence { yield(element) }

    override fun asSingle(): T = element

    override fun filter(fn: (element: T) -> Boolean): ElementSource<T> {
        return if (fn(element)) this else EmptyElementSource
    }
}

data class MultipleElementSource<T : Any>(val elements: Iterable<T>) : ElementSource<T>() {

    override fun asSequence() = elements.asSequence()

    override fun asSingle(): T = elements.firstOrNull() ?: throw NoSuchElementException("There are no elements.")

    override fun filter(fn: (element: T) -> Boolean): ElementSource<T> {
        return fromIterable(elements.filter(fn))
    }
}


