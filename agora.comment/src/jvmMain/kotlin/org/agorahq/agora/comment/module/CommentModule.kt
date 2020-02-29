package org.agorahq.agora.comment.module

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.module.base.BaseModule

class CommentModule(
        operations: Iterable<AnyContentOperation> = listOf()
) : BaseModule<Comment>(operations, Comment::class) {

    override val name = "Comments"
}