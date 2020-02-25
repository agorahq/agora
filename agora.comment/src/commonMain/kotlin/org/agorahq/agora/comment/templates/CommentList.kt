package org.agorahq.agora.comment.templates

import kotlinx.html.dd
import kotlinx.html.div
import kotlinx.html.dl
import kotlinx.html.dt
import kotlinx.html.h3
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.operations.CommentCreator
import org.agorahq.agora.comment.operations.CommentLister
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.extensions.htmlContent
import org.agorahq.agora.core.extensions.markdownContent
import org.agorahq.agora.core.extensions.parentOf
import org.agorahq.agora.core.extensions.whenHasOperation
import org.agorahq.agora.core.template.template

val COMMENT_LIST = template<CommentListParams> { (parent, operation, site, comments) ->
    h3 { +"Comments" }
    dl {
        comments.forEach { comment ->
            dt { +comment.author }
            dd { markdownContent(comment.rawContent) }
        }
    }
    site.parentOf(operation).whenHasOperation<CommentCreator> {
        div {
            htmlContent(it.renderFormFor(parent))
        }
    }
}

data class CommentListParams(
        val parent: Page,
        val operation: CommentLister,
        val site: Site,
        val comments: Sequence<Comment>)