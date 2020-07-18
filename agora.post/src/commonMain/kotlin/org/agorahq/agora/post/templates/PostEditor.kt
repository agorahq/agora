package org.agorahq.agora.post.templates

import kotlinx.html.HTML
import kotlinx.html.h1
import kotlinx.html.h6
import kotlinx.html.strong
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.post.viewmodel.PostViewModel

fun HTML.renderPostEditor(
        post: PostViewModel,
        context: OperationContext<out Any>
) = withDefaultLayout(
        context = context,
        pageTitle = "Editing ${post.title} | Agora"
) {
    h1("mt-3") {
        +post.title
        h6("text-muted text-right") { +post.publicationDate }
    }
    strong { +"WIP" }
}
