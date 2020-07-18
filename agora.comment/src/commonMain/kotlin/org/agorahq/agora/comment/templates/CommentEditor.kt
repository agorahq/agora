package org.agorahq.agora.comment.templates

import kotlinx.html.HTML
import kotlinx.html.h1
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout

fun HTML.renderCommentEditor(
        context: OperationContext<out Any>,
        comment: CommentViewModel
) = withDefaultLayout(
        context = context,
        pageTitle = "Editing ${comment.username}'s comment (${comment.id}) | Agora"
) {
    h1("mt-3") {
        +"Edit Comment"
    }
    renderCommentForm(comment)
}
