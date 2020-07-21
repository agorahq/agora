package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.DeleteCommentURL
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.hexworks.cobalt.core.api.UUID

class DeleteComment(
        private val commentQueryService: QueryService<Comment>,
        private val commentStorage: StorageService<Comment>
) : Procedure<Comment, UUID>, ProcedureDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        return ElementSource.ofMaybe(commentQueryService.findById(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = Command.of {
        commentStorage.delete(data.asSingle())
    }

    companion object : ProcedureDescriptor<Comment, UUID> {
        override val name = "Delete comment"
        override val attributes = Attributes.create<Comment, UUID, Unit>(
                route = DeleteCommentURL.route,
                urlClass = DeleteCommentURL::class
        )
    }
}
