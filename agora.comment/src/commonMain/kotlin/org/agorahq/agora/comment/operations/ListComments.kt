package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.COMMENT_LIST
import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.module.renderer.ChildResourceListRenderer
import org.agorahq.agora.core.api.service.ChildResourceQueryService

class ListComments(
        private val commentService: ChildResourceQueryService<Comment>
) : ChildResourceListRenderer<Comment, ResourceContext<Page>> {

    override val resourceClass = Comment::class
    override val route = CommentURL.root

    override fun ResourceContext<Page>.reify() = {
        COMMENT_LIST.render(commentService
                .findByParent(resource)
                .toViewListingContext())
    }.toCommand()


}
