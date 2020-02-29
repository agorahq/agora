package org.agorahq.agora.core.platform

import org.agorahq.agora.core.api.services.MarkdownRenderer

expect object MarkdownRendererFactory {

    fun createRenderer(): MarkdownRenderer
}