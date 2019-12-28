package org.agorahq.agora.core.template

import kotlinx.html.TagConsumer
import org.agorahq.agora.core.template.internal.HTMLTemplate

fun partial(builder: TagConsumer<*>.(Unit) -> Unit): Template<Unit, String> {
    return HTMLTemplate(
            builder = builder)
}

fun <D> template(builder: TagConsumer<*>.(D) -> Unit): Template<D, String> {
    return HTMLTemplate(
            builder = builder)
}

