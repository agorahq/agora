package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Facet
import org.agorahq.agora.core.api.operation.OperationType.ResourceSaver
import org.agorahq.agora.core.api.operation.SaveResource
import org.agorahq.agora.core.api.operation.SaveResourceDescriptor
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.core.api.view.ConverterService

class CreateComment(
        private val commentStorage: StorageService<Comment>,
        private val converterService: ConverterService
) : SaveResource<Comment, CommentViewModel>, SaveResourceDescriptor<Comment, CommentViewModel> by Companion {

    override fun fetchResource(context: ViewModelContext<CommentViewModel>): ElementSource<Comment> {
        val enrichedModel = context.viewModel.copy(
                userId = context.user.id.toString(),
                username = context.user.username)
        return ElementSource.fromElement(converterService.convertToResource<Comment>(enrichedModel).get())
    }

    override fun createCommand(context: ViewModelContext<CommentViewModel>, data: ElementSource<Comment>) = {
        commentStorage.create(data.asSingle())
    }.toCommand()

    companion object : SaveResourceDescriptor<Comment, CommentViewModel> {
        override val name = "Create Comment"
        override val resourceClass = Comment::class
        override val type = ResourceSaver(Comment::class, CommentViewModel::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
        override val facets = listOf<Facet>()
    }
}
