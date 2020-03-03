package org.agorahq.agora.post.converter

import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.view.ResourceConverter
import org.agorahq.agora.core.platform.MarkdownRendererFactory
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.viewmodel.PostViewModel

class PostConverter(
        private val userService: QueryService<User>
) : ResourceConverter<PostViewModel, Post> {

    override val viewModelClass = PostViewModel::class
    override val resourceClass = Post::class

    override fun PostViewModel.toResource() = Post(
            title = title,
            shortDescription = shortDescription,
            tags = setOf(),
            owner = userService.findById(ownerId.toUUID()).get(),
            content = content)

    override fun Post.toViewModel() = PostViewModel(
            ownerId = owner.id.toString(),
            shortDescription = shortDescription,
            url = url.generate(),
            title = title,
            tags = tags,
            content = MarkdownRendererFactory.createRenderer().render(content))
}