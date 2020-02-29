package org.agorahq.agora.core.platform

import org.agorahq.agora.core.services.FlexmarkRenderer
import org.agorahq.agora.core.api.services.MarkdownRenderer

actual object MarkdownRendererFactory {

    private val renderer = FlexmarkRenderer

    actual fun createRenderer(): MarkdownRenderer {
        return renderer
    }
}