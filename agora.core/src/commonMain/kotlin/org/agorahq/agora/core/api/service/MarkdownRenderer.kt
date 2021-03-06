package org.agorahq.agora.core.api.service

/**
 * Can be used to render markdown as HTML.
 */
interface MarkdownRenderer {

    /**
     * Renders the given [markdown] as a HTML [String].
     */
    fun render(markdown: String): String
}