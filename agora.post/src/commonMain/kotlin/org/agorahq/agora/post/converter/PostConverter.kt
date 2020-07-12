package org.agorahq.agora.post.converter

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.date.Dates.humanReadableFormat
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
            abstract = abstract,
            tags = setOf(),
            owner = userService.findById(ownerId.toUUID()).get(),
            content = content)

    override fun Post.toViewModel(context: OperationContext) = PostViewModel(
            ownerId = owner.id.toString(),
            abstract = abstract,
            url = url.generate(),
            title = title,
            tags = tags,
            content = MarkdownRendererFactory.createRenderer().render(content),
            context = context,
            renderedPageElements = "",
            excerpt = excerpt,
            publicationDate = publishedAt.format(humanReadableFormat),
            isPublished = publishedAt.unixMillisLong < DateTime.nowUnixLong(),
            id = id.toString()
    )
}
