package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.COMMENT_FORM
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationType.PageElementFormRenderer
import org.agorahq.agora.core.api.operation.RenderPageElementForm
import org.agorahq.agora.core.api.operation.RenderPageElementFormDescriptor
import org.agorahq.agora.core.api.operation.context.PageContext

class ShowCommentForm : RenderPageElementForm<Comment, Page>, RenderPageElementFormDescriptor<Comment, Page> by Companion {

    private val operation = this

    override fun PageContext<Page>.createCommand() = {
        authorization.tryAuthorize(user, page.owner, operation)
        COMMENT_FORM.render(CommentViewModel(
                parentId = page.id.toString(),
                content = "",
                username = user.username,
                userId = user.id.toString()))
    }.toCommand()

    companion object : RenderPageElementFormDescriptor<Comment, Page> {
        override val name = "Show Comment Form"
        override val resourceClass = Comment::class
        override val type = PageElementFormRenderer(Comment::class, Page::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
    }
}
