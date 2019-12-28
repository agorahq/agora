package org.agorahq.agora.core.template

/**
 * A [Template] is a blueprint for a document which can be rendered using the supplied
 * [D] into a format of type [R].
 */
interface Template<D, R> {

    /**
     * Renders this [Template] using the given [data] and returns it.
     */
    fun render(data: D): R

}