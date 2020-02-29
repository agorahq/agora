package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.COMMENT_LIST
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.module.renderer.PageContentResourceListRenderer
import org.agorahq.agora.core.api.services.DocumentElementQueryService

class ListComments(
        private val commentService: DocumentElementQueryService<Comment>
) : PageContentResourceListRenderer<Comment, ResourceContext<Page>> {

    override val resourceClass = Comment::class
    override val route = CommentURL.root

    override fun ResourceContext<Page>.execute() = COMMENT_LIST
            .render(toListingContext(commentService.findByParent(this.resource)))


}
