package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.Procedure
import org.agorahq.agora.core.api.operation.ProcedureDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ResourceSaver
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
        return ElementSource.fromElement(converterService.convertToResource<Comment>(enrichedModel).get())
    }

    override fun createCommand(context: OperationContext<out CommentViewModel>, data: ElementSource<Comment>) = {
        commentStorage.create(data.asSingle())
    }.toCommand()

    companion object : ProcedureDescriptor<Comment, CommentViewModel> by OperationDescriptor.create(
            name = "Create Comment",
            route = CommentURL.root,
            urlClass = CommentURL::class,
            facets = listOf(ResourceSaver)
    )
}
