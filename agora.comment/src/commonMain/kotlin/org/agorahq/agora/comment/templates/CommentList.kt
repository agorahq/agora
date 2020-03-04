package org.agorahq.agora.comment.templates

import kotlinx.html.dd
import kotlinx.html.dl
import kotlinx.html.dt
import kotlinx.html.h3
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.core.api.extensions.htmlContent
import org.agorahq.agora.core.api.template.template

val COMMENT_LIST = template<CommentListViewModel> { model ->
    h3 { +"Comments" }
    dl {
        model.comments.forEach { comment ->
            dt { +comment.username }
            dd { htmlContent(comment.content) }
        }
    }
}