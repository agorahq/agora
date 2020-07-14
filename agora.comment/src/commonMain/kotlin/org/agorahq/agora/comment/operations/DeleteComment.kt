package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.Procedure
import org.agorahq.agora.core.api.operation.ProcedureDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ResourceAlterer
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.hexworks.cobalt.core.api.UUID

class DeleteComment(
        private val commentQueryService: QueryService<Comment>,
        private val commentStorage: StorageService<Comment>
) : Procedure<Comment, UUID>, ProcedureDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        return ElementSource.fromMaybe(commentQueryService.findById(context.input))
    }

    override fun createCommand(context: OperationContext<out UUID>, data: ElementSource<Comment>) = {
        commentStorage.delete(data.asSingle())
    }.toCommand()

    companion object : ProcedureDescriptor<Comment, UUID> by OperationDescriptor.create(
            name = "Delete Comment",
            route = CommentURL.root,
            urlClass = CommentURL::class,
            facets = listOf(ResourceAlterer)
    )
}
