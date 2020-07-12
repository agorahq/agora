package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.htmlContent
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.post.viewmodel.PostViewModel

fun HTML.renderPostDetails(
        model: PostViewModel,
        ctx: OperationContext
) = withDefaultLayout(
        ctx = ctx,
        pageTitle = "${model.title} | Agora"
) {
    h1("mt-3") {
        +model.title
    }
    div {
        htmlContent(model.content)
    }
    div {
        model.tags.forEach { tag ->
            span("badge badge-secondary mr-1") { +tag }
        }
    }
    hr { }
    div {
        htmlContent(model.renderedPageElements)
    }
}
