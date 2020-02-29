package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.ResourceOperation
import org.agorahq.agora.core.api.services.StorageService

class DeleteComment(
        private val commentStorage: StorageService<Comment>
) : ResourceOperation<Comment> {

    override val resourceClass = Comment::class
    override val route = CommentURL.root

    override fun ResourceContext<Comment>.execute() = commentStorage.create(resource)

    override fun toString() = "Operation: Delete Comment"

}