package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.templates.COMMENT_LIST
import org.agorahq.agora.comment.templates.CommentListParams
import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.operations.PageContentLister
import org.agorahq.agora.core.services.DocumentElementQueryService

class CommentLister(
        private val site: Site,
        private val commentQueryService: DocumentElementQueryService<Comment>
) : PageContentLister {

    override fun renderListingFor(page: Page): String {
        return COMMENT_LIST.render(CommentListParams(
                parent = page,
                operation = this,
                site = site,
                comments = commentQueryService.findByParent(page)))
    }
}