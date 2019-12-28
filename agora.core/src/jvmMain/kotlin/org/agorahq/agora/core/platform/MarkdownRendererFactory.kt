package org.agorahq.agora.core.platform

import org.agorahq.agora.core.service.FlexmarkRenderer
import org.agorahq.agora.core.service.MarkdownRenderer

actual object MarkdownRendererFactory {

    private val renderer = FlexmarkRenderer

    actual fun createRenderer(): MarkdownRenderer {
        return renderer
    }
}