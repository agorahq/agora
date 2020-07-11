package org.agorahq.agora.comment.templates

import kotlinx.html.*
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.core.api.extensions.htmlContent

fun FlowContent.renderCommentList(model: CommentListViewModel) {
    h3 { +"Comments" }
    dl {
        model.comments.forEach { comment ->
            dt { +comment.username }
            dd { htmlContent(comment.content) }
        }
    }
}

