package org.agorahq.agora.comment.module

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.module.base.BaseModule
import org.agorahq.agora.core.api.operation.Operation

// TODO: I'm not sure we need dedicated module implementations
class CommentModule(
        initialOperations: Iterable<Operation<out Resource, out Any, out Any>> = listOf()
) : BaseModule<Comment>(
        initialOperations = initialOperations,
        resourceClass = Comment::class
) {

    override val name = "Comments"
}
