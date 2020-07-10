package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.DeleteResource
import org.agorahq.agora.core.api.operation.DeleteResourceDescriptor
import org.agorahq.agora.core.api.operation.OperationType.ResourceDeleter
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService

class DeleteComment(
        private val commentQueryService: QueryService<Comment>,
        private val commentStorage: StorageService<Comment>
) : DeleteResource<Comment>, DeleteResourceDescriptor<Comment> by Companion {

    override fun ResourceIdContext.fetchData(): ElementSource<Comment> {
        return ElementSource.fromMaybe(commentQueryService.findById(id))
    }

    override fun ResourceIdContext.createCommand(data: ElementSource<Comment>) = {
        commentStorage.delete(data.asSingle())
    }.toCommand()

    companion object : DeleteResourceDescriptor<Comment> {
        override val name = "Delete Comment"
        override val resourceClass = Comment::class
        override val type = ResourceDeleter(Comment::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
    }
}
