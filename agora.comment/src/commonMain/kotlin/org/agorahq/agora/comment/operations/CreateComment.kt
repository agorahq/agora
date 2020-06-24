package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.extensions.toCommand
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

    override fun ViewModelContext<CommentViewModel>.createCommand() = {
        val enrichedModel = viewModel.copy(
                userId = user.id.toString(),
                username = user.username)
        commentStorage.create(converterService.convertToResource<Comment>(enrichedModel).get())
    }.toCommand()

    companion object : SaveResourceDescriptor<Comment, CommentViewModel> {
        override val name = "Create Comment"
        override val resourceClass = Comment::class
        override val type = ResourceSaver(Comment::class, CommentViewModel::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
    }
}
