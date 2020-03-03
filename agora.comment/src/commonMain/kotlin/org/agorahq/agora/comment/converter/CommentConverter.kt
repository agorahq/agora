package org.agorahq.agora.comment.converter

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.exception.EntityNotFoundException
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.view.ResourceConverter
import org.agorahq.agora.core.platform.MarkdownRendererFactory
import org.agorahq.agora.core.platform.SystemUtils
import org.hexworks.cobalt.core.api.UUID

class CommentConverter(
        private val userService: QueryService<User>
) : ResourceConverter<CommentViewModel, Comment> {

    override val viewModelClass = CommentViewModel::class
    override val resourceClass = Comment::class

    override fun CommentViewModel.toResource() = Comment(
            owner = userService.findById(userId.toUUID()).orElseThrow { EntityNotFoundException(User::class, userId) },
            parentId = parentId.toUUID(),
            createdAt = SystemUtils.currentTimeMillis(),
            updatedAt = SystemUtils.currentTimeMillis(),
            publishedAt = SystemUtils.currentTimeMillis(),
            content = content,
            id = id?.toUUID() ?: UUID.randomUUID())


    override fun Comment.toViewModel() = CommentViewModel(
            id = id.toString(),
            parentId = parentId.toString(),
            content = MarkdownRendererFactory.createRenderer().render(content),
            userId = owner.id.toString(),
            username = owner.username)
}