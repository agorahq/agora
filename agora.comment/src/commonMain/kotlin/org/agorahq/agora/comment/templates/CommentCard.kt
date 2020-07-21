package org.agorahq.agora.comment.templates

import kotlinx.html.TagConsumer
import kotlinx.html.div
import kotlinx.html.h6
import kotlinx.html.unsafe
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.extensions.renderFormsFor
import org.agorahq.agora.core.api.extensions.renderResourceLinksFor
import org.agorahq.agora.core.api.operation.context.OperationContext

fun TagConsumer<Appendable>.renderCommentCard(
        context: OperationContext<out Any>,
        comment: CommentViewModel
) {
    val classes = if (comment.isHidden) {
        "card text-secondary bg-light border-warning mb-3"
    } else {
        "card bg-light mb-3"
    }
    val editing = comment.editing
    div(classes) {
        div("card-body") {
            h6("card-title") { +comment.username }
            if (editing) {
                unsafe {
                    +context.renderFormsFor(comment.id, Comment::class)
                }
            } else {
                div("card-text") {
                    unsafe {
                        +comment.content
                    }
                }
            }
        }
        if (editing.not()) {
            val alterers = context.renderResourceLinksFor(Comment::class, comment.id)
            if (alterers.isNotBlank()) {
                div("card-footer bg-transparent") {
                    unsafe {
                        +alterers
                    }
                }
            }
        }
    }
}


