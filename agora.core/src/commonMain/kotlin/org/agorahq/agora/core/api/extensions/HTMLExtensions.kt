package org.agorahq.agora.core.api.extensions

import kotlinx.html.HTMLTag
import kotlinx.html.unsafe
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.platform.MarkdownRendererFactory

/**
 * Renders the given [page] at the current position in the HTML document.
 */
fun HTMLTag.documentContent(page: Page) {
    unsafe {
        +MarkdownRendererFactory.createRenderer().render(page.content)
    }
}

/**
 * Renders the given [htmlContent] at the current position in the HTML document.
 */
fun HTMLTag.htmlContent(htmlContent: String) {
    unsafe {
        +htmlContent
    }
}
