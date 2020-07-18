package org.agorahq.agora.core.api.operation

/**
 * Represents an unique quality or property of an [OperationDescriptor].
 */
interface Attribute {

    /**
     * Tells whether this [Attribute] is a match for the [other] [Attribute]. This function
     * can be used to filter [Attribute]s of an [Operation].
     * An example of this is to filter for all operations having TODO
     */
    fun matches(other: Attribute): Boolean = this == other
}
