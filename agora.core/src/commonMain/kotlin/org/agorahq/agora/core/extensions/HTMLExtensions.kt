package org.agorahq.markland.core.extensions

import kotlinx.html.*
import org.agorahq.markland.core.collection.DocumentMetadata
import org.agorahq.markland.core.template.Template

fun <T> TagConsumer<*>.include(template: Template<T>, ctx: T) {
    doInclude(template, this, ctx)
}

fun TagConsumer<*>.include(template: Template<Unit>) {
    doInclude(template, this)
}

fun <T> FlowContent.include(template: Template<T>, ctx: T) {
    doInclude(template, consumer, ctx)
}

fun FlowContent.include(template: Template<Unit>) {
    doInclude(template, consumer)
}

fun HTML.include(template: Template<Unit>) {
    doInclude(template, consumer)
}

fun <T> HTML.include(template: Template<T>, data: T) {
    doInclude(template, consumer, data)
}

fun HTMLTag.content(documentMetadata: DocumentMetadata) {
    unsafe {
        +documentMetadata.markdownContent
    }
}

fun <T> HTMLTag.content(layout: Template<T>, data: T) {
    doInclude(layout, consumer, data)
}

private fun doInclude(template: Template<Unit>, consumer: TagConsumer<*>) {
    doInclude(template, consumer, Unit)
}

private fun <T> doInclude(template: Template<T>, consumer: TagConsumer<*>, data: T) {
    consumer.onTagContentUnsafe {
        +template.build(data)
    }
}
