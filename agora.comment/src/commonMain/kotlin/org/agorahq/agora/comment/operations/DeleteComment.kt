package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.DeleteResource
import org.agorahq.agora.core.api.operation.DeleteResourceDescriptor
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.security.OperationType.ResourceDeleter
import org.agorahq.agora.core.api.service.StorageService

class DeleteComment(
        private val commentStorage: StorageService<Comment>
) : DeleteResource<Comment>, DeleteResourceDescriptor<Comment> by Companion {

    override fun ResourceIdContext.createCommand() = {
        commentStorage.deleteById(id)
    }.toCommand()

    companion object : DeleteResourceDescriptor<Comment> {

        override val name = "Delete Comment"
        override val resourceClass = Comment::class
        override val type = ResourceDeleter(Comment::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class

    }
}