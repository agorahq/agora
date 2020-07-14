package org.agorahq.agora.core.api.operation

/**
 * Represents an unique quality or property of an [OperationDescriptor].
 */
interface Facet {

    /**
     * Tells whether this [Facet] is a match for the [other] [Facet]. This function
     * can be used to filter [Facet]s of an [Operation].
     * An example of this is to filter for all operations having TODO
     */
    fun matches(other: Facet): Boolean = this == other
}
