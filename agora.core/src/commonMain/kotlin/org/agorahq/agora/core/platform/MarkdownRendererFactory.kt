package org.agorahq.agora.core.platform

import org.agorahq.agora.core.service.MarkdownRenderer

expect object MarkdownRendererFactory {

    fun createRenderer(): MarkdownRenderer
}