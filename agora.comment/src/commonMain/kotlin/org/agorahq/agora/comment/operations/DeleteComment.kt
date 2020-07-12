package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.AlterResource
import org.agorahq.agora.core.api.operation.AlterResourceDescriptor
import org.agorahq.agora.core.api.operation.OperationType.ResourceAlterer
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService

class DeleteComment(
        private val commentQueryService: QueryService<Comment>,
        private val commentStorage: StorageService<Comment>
) : AlterResource<Comment>, AlterResourceDescriptor<Comment> by Companion {

    override fun ResourceIdContext.fetchData(): ElementSource<Comment> {
        return ElementSource.fromMaybe(commentQueryService.findById(id))
    }

    override fun ResourceIdContext.createCommand(data: ElementSource<Comment>) = {
        commentStorage.delete(data.asSingle())
    }.toCommand()

    companion object : AlterResourceDescriptor<Comment> {
        override val name = "Delete Comment"
        override val resourceClass = Comment::class
        override val type = ResourceAlterer(Comment::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
    }
}
