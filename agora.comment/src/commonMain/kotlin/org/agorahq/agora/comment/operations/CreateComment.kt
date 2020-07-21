package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CreateCommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.core.api.view.ConverterService

class CreateComment(
        private val commentStorage: StorageService<Comment>,
        private val converterService: ConverterService
) : Procedure<Comment, CommentViewModel>, ProcedureDescriptor<Comment, CommentViewModel> by Companion {

    override fun fetchResource(context: OperationContext<out CommentViewModel>): ElementSource<Comment> {
        val enrichedModel = context.input.copy(
                userId = context.user.id.toString(),
                username = context.user.username)
        return ElementSource.of(converterService.convertToResource<Comment>(enrichedModel).get())
    }

    override fun createCommand(
            context: OperationContext<out CommentViewModel>,
            data: ElementSource<Comment>
    ) = Command.of {
        commentStorage.create(data.asSingle())
    }

    companion object : ProcedureDescriptor<Comment, CommentViewModel> {
        override val name = "Create comment"
        override val attributes = Attributes.create<Comment, CommentViewModel, Unit>(
                route = CreateCommentURL.root,
                urlClass = CreateCommentURL::class
        )
    }
}
