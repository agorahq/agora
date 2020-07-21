package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.htmlContent
import org.agorahq.agora.core.api.extensions.renderPageElementsFor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.post.viewmodel.PostViewModel

fun HTML.renderPostDetails(
        post: PostViewModel,
        ctx: OperationContext<out Any>
) = withDefaultLayout(
        context = ctx,
        pageTitle = "${post.title} | Agora"
) {
    h1("mt-3") {
        +post.title
        h6("text-muted text-right") { +post.publicationDate }
    }
    div {
        if (post.abstract.isNotBlank()) {
            div("card my-3") {
                div("card-body text-secondary") { +post.abstract }
            }
        }
        htmlContent(post.content)
    }
    div {
        post.tags.forEach { tag ->
            span("badge badge-secondary mr-1") { +tag }
        }
    }
    hr { }
    div {
        unsafe {
            +ctx.renderPageElementsFor(post.id)
        }
    }
}
