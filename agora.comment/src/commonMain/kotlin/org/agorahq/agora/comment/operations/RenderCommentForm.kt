package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.COMMENT_FORM
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.module.renderer.ContentFormRenderer

class RenderCommentForm : ContentFormRenderer<Comment> {

    override val resourceClass = Comment::class
    override val route = CommentURL.root

    override fun ResourceContext<out Page>.execute() = COMMENT_FORM.render(this)


}