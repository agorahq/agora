package org.agorahq.agora.core.api.data

/**
 * A [ResourceURL] represents the coordinates (URL) where the given
 * [Resource] can be accessed. This object can be used to [generate]
 * urls and to check whether a given [Resource] [matches] this URL.
 */
interface ResourceURL<R : Resource> {

    val redirectTo: String?
    val pageElementsToEdit: Iterable<String>

    /**
     * Generates the textual representation of this [ResourceURL].
     */
    fun generate(): String

    /**
     * Tells whether this [ResourceURL] matches the given [resource].
     */
    fun matches(resource: R): Boolean

    companion object {

        fun <R : Resource> toString(resourceURL: ResourceURL<R>) = resourceURL.generate()
    }
}
