package org.agorahq.agora.core.platform

import org.agorahq.agora.core.services.MarkdownRenderer

expect object MarkdownRendererFactory {

    fun createRenderer(): MarkdownRenderer
}