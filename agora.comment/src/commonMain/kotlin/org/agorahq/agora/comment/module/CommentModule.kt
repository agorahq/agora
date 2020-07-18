package org.agorahq.agora.comment.module

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.base.BaseModule
import org.agorahq.agora.core.api.operation.Operation

class CommentModule(
        operations: Iterable<Operation<out Resource, out Any, out Any>> = listOf()
) : BaseModule<Comment>(
        operations = operations,
        resourceClass = Comment::class
) {

    override val name = "Comments"
}
