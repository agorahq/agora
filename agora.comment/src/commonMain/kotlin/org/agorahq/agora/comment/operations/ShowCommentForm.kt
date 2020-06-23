package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.COMMENT_FORM
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.RenderPageElementForm
import org.agorahq.agora.core.api.operation.RenderPageElementFormDescriptor
import org.agorahq.agora.core.api.operation.context.PageContext
import org.agorahq.agora.core.api.security.OperationType.PageElementFormRenderer

class ShowCommentForm : RenderPageElementForm<Comment, Page>, RenderPageElementFormDescriptor<Comment> by Companion {

    override val descriptor = ShowCommentForm

    private val operation = this

    override fun PageContext<Page>.createCommand() = {
        authorization.tryAuthorize(user, page.owner, operation)
        COMMENT_FORM.render(CommentViewModel(
                parentId = page.id.toString(),
                content = "",
                username = user.username,
                userId = user.id.toString(),
                owner = user))
    }.toCommand()

    companion object : RenderPageElementFormDescriptor<Comment> {

        override val name = "Show Comment Form"
        override val resourceClass = Comment::class
        override val type = PageElementFormRenderer(Comment::class, Page::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class

    }
}
