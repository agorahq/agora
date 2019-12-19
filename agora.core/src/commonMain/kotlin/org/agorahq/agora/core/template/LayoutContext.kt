package org.agorahq.markland.core.template

import org.agorahq.markland.core.collection.DocumentMetadata
import org.agorahq.markland.core.collection.Site

/**
 * Context data for rendering layouts.
 */
data class LayoutContext<T>(val documentMetadata: DocumentMetadata,
                            val site: Site,
                            val data: T)