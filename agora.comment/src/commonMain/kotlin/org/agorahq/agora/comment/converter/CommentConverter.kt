package org.agorahq.agora.comment.converter

import com.soywiz.klock.DateTime
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.exception.EntityNotFoundException
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.view.ResourceConverter
import org.agorahq.agora.core.platform.MarkdownRendererFactory
import org.hexworks.cobalt.core.api.UUID

class CommentConverter(
        private val userService: QueryService<User>
) : ResourceConverter<CommentViewModel, Comment> {

    override val viewModelClass = CommentViewModel::class
    override val resourceClass = Comment::class

    override fun CommentViewModel.toResource() = Comment(
            owner = userService.findById(userId.toUUID()).orElseThrow { EntityNotFoundException(User::class, userId) },
            parentId = parentId.toUUID(),
            createdAt = DateTime.now(),
            content = content,
            id = id?.toUUID() ?: UUID.randomUUID())


    override fun Comment.toViewModel(context: OperationContext<out Any>) = CommentViewModel(
            id = id.toString(),
            parentId = parentId.toString(),
            content = MarkdownRendererFactory.createRenderer().render(content),
            userId = owner.id.toString(),
            username = owner.username
    )
}
