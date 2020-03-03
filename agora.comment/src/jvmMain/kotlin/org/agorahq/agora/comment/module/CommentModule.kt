package org.agorahq.agora.comment.module

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.api.module.base.BaseModule

class CommentModule(
        operations: Iterable<AnyContentOperation> = listOf()
) : BaseModule<Comment, CommentViewModel>(operations, Comment::class, CommentViewModel::class) {

    override val name = "Comments"
}