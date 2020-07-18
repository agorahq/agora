package org.agorahq.agora.comment.templates

import kotlinx.html.*
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.core.api.extensions.renderResourceLinksFor
import org.agorahq.agora.core.api.operation.context.OperationContext

fun TagConsumer<Appendable>.renderCommentList(
        context: OperationContext<out Any>,
        model: CommentListViewModel
) {
    val comments = model.comments.toList()
    if (comments.isNotEmpty()) {
        h3 { +"Comments" }
        model.comments.forEach { comment ->
            renderCommentCard(context, comment)
        }
    }
}


