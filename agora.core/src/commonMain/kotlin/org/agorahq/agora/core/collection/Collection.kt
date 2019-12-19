package org.agorahq.markland.core.collection

import org.agorahq.markland.core.extensions.Layout

/**
 * Represents a collection of [DocumentMetadata]s and their
 * [CollectionConfiguration].
 */
data class Collection<T>(
        val config: CollectionConfiguration<T>,
        val documents: Iterable<DocumentMetadata>) {

    val layout: Layout<T>
        get() = config.layout
}