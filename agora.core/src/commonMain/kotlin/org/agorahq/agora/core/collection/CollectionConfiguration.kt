package org.agorahq.markland.core.collection

import org.agorahq.markland.core.extensions.Layout

/**
 * Configuration for a [Collection].
 */
data class CollectionConfiguration<T>(
        val name: String,
        val layout: Layout<T>)