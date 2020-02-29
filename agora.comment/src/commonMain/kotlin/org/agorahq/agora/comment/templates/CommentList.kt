package org.agorahq.agora.comment.templates

import kotlinx.html.dd
import kotlinx.html.dl
import kotlinx.html.dt
import kotlinx.html.h3
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.extensions.markdownContent
import org.agorahq.agora.core.api.module.context.ResourceListingContext
import org.agorahq.agora.core.api.template.template

val COMMENT_LIST = template<ResourceListingContext<Comment>> { ctx ->
    h3 { +"Comments" }
    dl {
        ctx.items.forEach { comment ->
            dt { +comment.author }
            dd { markdownContent(comment.content) }
        }
    }
}