package org.agorahq.agora.comment.templates

import kotlinx.html.dd
import kotlinx.html.div
import kotlinx.html.dl
import kotlinx.html.dt
import kotlinx.html.h3
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.facets.CommentCreating
import org.agorahq.agora.comment.facets.CommentListing
import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.extensions.htmlContent
import org.agorahq.agora.core.extensions.markdownContent
import org.agorahq.agora.core.extensions.parentOf
import org.agorahq.agora.core.extensions.whenHasFacet
import org.agorahq.agora.core.template.template

val COMMENT_LIST = template<CommentListParams> { (parent, facet, site, comments) ->
    h3 { +"Comments" }
    dl {
        comments.forEach { comment ->
            dt { +comment.author }
            dd { markdownContent(comment.markdownContent) }
        }
    }
    site.parentOf(facet).whenHasFacet<CommentCreating> {
        div {
            htmlContent(it.renderFormFor(parent))
        }
    }
}

data class CommentListParams(
        val parent: Document,
        val facet: CommentListing,
        val site: Site,
        val comments: Sequence<Comment>)