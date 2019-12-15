package org.agorahq.markland.core.template

import kotlinx.html.TagConsumer
import org.agorahq.markland.core.template.internal.KotlinxHTMLTemplate
import kotlin.jvm.JvmName

fun <T> template(builder: TagConsumer<*>.(T) -> Unit): Template<T> {
    return KotlinxHTMLTemplate(builder)
}

fun partial(builder: TagConsumer<*>.(Unit) -> Unit): Template<Unit> {
    return KotlinxHTMLTemplate(builder)
}

fun <T> layout(builder: TagConsumer<*>.(LayoutContext<T>) -> Unit): Template<LayoutContext<T>> {
    return KotlinxHTMLTemplate(builder)
}

@JvmName("simpleLayout")
fun layout(builder: TagConsumer<*>.(LayoutContext<Unit>) -> Unit): Template<LayoutContext<Unit>> {
    return KotlinxHTMLTemplate(builder)
}

