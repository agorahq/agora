package org.agorahq.agora.comment.module

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.module.base.BaseModule
import org.agorahq.agora.core.api.operation.AnyOperation

class CommentModule(
        operations: Iterable<AnyOperation> = listOf()
) : BaseModule<Comment, CommentViewModel>(
        operations = operations,
        resourceClass = Comment::class,
        viewModelClass = CommentViewModel::class
) {

    override val name = "Comments"
}
