package org.agorahq.agora.comment.module

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.module.Operation
import org.agorahq.agora.core.module.base.BaseModule

class CommentModule(
        operations: Iterable<Operation> = listOf()
) : BaseModule<Comment>(operations) {

    override val name = "Comments"
}