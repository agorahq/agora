package org.agorahq.agora.core.api.data

import org.hexworks.cobalt.datatypes.Maybe

/**
 * An [ElementSource] is an Algebraic Data Structure which abstracts the notion
 * of a stream of elements (of type [T]). There are 3 variants:
 * - [SingleElementSource]: contains a single element of type [T]
 * - [MultipleElementSource]: contains multiple elements of type [T]
 * - [EmptyElementSource]: contains no elements.
 * In Functional Programming terms an [ElementSource] is a *Functor*.
 */
sealed class ElementSource<out T : Any> {

    abstract fun asSequence(): Sequence<T>

    abstract fun asSingle(): T

    abstract fun filter(fn: (element: T) -> Boolean): ElementSource<T>

    abstract fun <R : Any> map(fn: (element: T) -> R): ElementSource<R>

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

    override fun <R : Any> map(fn: (element: Nothing) -> R) = this

}

data class SingleElementSource<T : Any>(val element: T) : ElementSource<T>() {

    override fun asSequence() = sequence { yield(element) }

    override fun asSingle(): T = element

    override fun filter(fn: (element: T) -> Boolean): ElementSource<T> {
        return if (fn(element)) this else EmptyElementSource
    }

    override fun <R : Any> map(fn: (element: T) -> R) = SingleElementSource(fn(element))
}

data class MultipleElementSource<T : Any>(val elements: Iterable<T>) : ElementSource<T>() {

    override fun asSequence() = elements.asSequence()

    override fun asSingle(): T = elements.firstOrNull() ?: throw NoSuchElementException("There are no elements.")

    override fun filter(fn: (element: T) -> Boolean): ElementSource<T> {
        return fromIterable(elements.filter(fn))
    }

    override fun <R : Any> map(fn: (element: T) -> R) = MultipleElementSource(elements.map(fn))
}


