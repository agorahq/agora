package org.agorahq.agora.comment.operations

import com.soywiz.klock.DateTime
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.UpdateCommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService

class UpdateComment(
        private val commentQueryService: QueryService<Comment>,
        private val commentStorage: StorageService<Comment>
) : Procedure<Comment, CommentViewModel>, ProcedureDescriptor<Comment, CommentViewModel> by Companion {

    override fun fetchResource(context: OperationContext<out CommentViewModel>): ElementSource<Comment> {
        val model = context.input
        return ElementSource.ofMaybe(commentQueryService.findById(model.id.toUUID()).map {
            it.copy(
                    updatedAt = DateTime.now(),
                    publishedAt = DateTime.now(),
                    content = model.content
            )
        })
    }

    override fun createCommand(
            context: OperationContext<out CommentViewModel>,
            data: ElementSource<Comment>
    ) = Command.of {
        commentStorage.update(data.asSingle())
    }

    companion object : ProcedureDescriptor<Comment, CommentViewModel> {
        override val name = "Update comment"
        override val attributes = Attributes.create<Comment, CommentViewModel, Unit>(
                route = UpdateCommentURL.route,
                urlClass = UpdateCommentURL::class,
                additionalAttributes = listOf()
        )
    }
}
