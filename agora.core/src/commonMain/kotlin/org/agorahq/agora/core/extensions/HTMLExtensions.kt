package org.agorahq.agora.core.extensions

import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.unsafe
import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.platform.MarkdownRendererFactory
import org.agorahq.agora.core.template.Template

/**
 * Renders the given [template] at the current position in the HTML document.
 */
fun <C> TagConsumer<*>.include(template: Template<C, String>, ctx: C) {
    doInclude(template, this, ctx)
}

/**
 * Renders the given [template] at the current position in the HTML document.
 */
fun <C> FlowContent.include(template: Template<C, String>, ctx: C) {
    doInclude(template, consumer, ctx)
}

/**
 * Renders the given [template] at the current position in the HTML document.
 */
fun <D> HTML.include(template: Template<D, String>, data: D) {
    doInclude(template, consumer, data)
}

/**
 * Renders the given [document] at the current position in the HTML document.
 */
fun HTMLTag.content(document: Document) {
    unsafe {
        +MarkdownRendererFactory.createRenderer().render(document.markdownContent)
    }
}

fun <D> HTMLTag.content(data: D, template: Template<D, String>) {
    unsafe {
        +template.render(data)
    }
}

private fun doInclude(template: Template<Unit, String>,
                      consumer: TagConsumer<*>) {
    doInclude(template, consumer, Unit)
}

private fun <D> doInclude(template: Template<D, String>,
                          consumer: TagConsumer<*>,
                          data: D) {
    consumer.onTagContentUnsafe {
        +template.render(data)
    }
}
