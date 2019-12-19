package org.agorahq.markland.core.template

interface Template<T> {

    /**
     * Combines the given [data] to create the final textual
     * representation of this [Template].
     */
    fun build(data: T): String

}