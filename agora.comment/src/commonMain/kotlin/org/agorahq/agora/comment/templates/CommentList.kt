package org.agorahq.agora.comment.templates

import kotlinx.html.TagConsumer
import kotlinx.html.div
import kotlinx.html.h3
import kotlinx.html.unsafe
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.core.api.extensions.renderPageElementFormsFor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.hexworks.cobalt.core.api.UUID

fun TagConsumer<Appendable>.renderCommentList(
        context: OperationContext<out Any>,
        parentId: UUID,
        model: CommentListViewModel
) {
    val comments = model.comments.toList()
    h3 { +"Comments" }
    div {
        unsafe {
            +context.renderPageElementFormsFor(parentId, Comment::class)
        }
    }
    if (comments.isNotEmpty()) {
        model.comments.forEach { comment ->
            renderCommentCard(context, comment)
        }
    }
}


