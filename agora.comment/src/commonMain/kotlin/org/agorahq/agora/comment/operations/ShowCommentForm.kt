package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.COMMENT_FORM
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.module.context.ViewContext
import org.agorahq.agora.core.api.module.renderer.FormRenderer

class ShowCommentForm : FormRenderer<Comment, CommentViewModel> {

    override val resourceClass = Comment::class
    override val route = CommentURL.root

    override fun ViewContext<CommentViewModel>.reify() = {
        COMMENT_FORM.render(this)
    }.toCommand()

}