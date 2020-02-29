package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.templates.COMMENT_LIST
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.operation.PageContentListRenderer
import org.agorahq.agora.core.api.services.DocumentElementQueryService

class ListComments(
        private val commentQueryService: DocumentElementQueryService<Comment>
) : PageContentListRenderer<Comment> {

    override fun render(context: OperationContext, parent: Page): String {
        return COMMENT_LIST.render(
                context.toPageContentListingContext(
                        items = commentQueryService.findByParent(parent),
                        parent = parent))
    }
}
