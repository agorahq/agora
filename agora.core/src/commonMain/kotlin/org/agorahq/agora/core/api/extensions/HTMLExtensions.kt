package org.agorahq.agora.core.api.extensions

import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer
import kotlinx.html.unsafe
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.platform.MarkdownRendererFactory
import org.agorahq.agora.core.api.template.Template

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
 * Renders the given [page] at the current position in the HTML document.
 */
fun HTMLTag.documentContent(page: Page) {
    unsafe {
        +MarkdownRendererFactory.createRenderer().render(page.content)
    }
}

fun HTMLTag.htmlContent(htmlContent: String) {
    unsafe {
        +htmlContent
    }
}

fun HTMLTag.markdownContent(markdownContent: String) {
    unsafe {
        +MarkdownRendererFactory.createRenderer().render(markdownContent)
    }
}

fun <D> HTMLTag.templateContent(data: D, template: Template<D, String>) {
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
