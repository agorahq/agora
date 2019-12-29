package org.agorahq.agora.comment.templates

import kotlinx.html.dd
import kotlinx.html.dl
import kotlinx.html.dt
import kotlinx.html.h3
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.extensions.markdownContent
import org.agorahq.agora.core.template.template

val COMMENT_LIST = template<Sequence<Comment>> { comments ->
    h3 { +"Comments" }
    dl {
        comments.forEach { comment ->
            dt { +comment.author }
            dd { markdownContent(comment.markdownContent) }
        }
    }
}