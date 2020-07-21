package org.agorahq.agora.post.converter

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.date.Dates.humanReadableFormat
import org.agorahq.agora.core.api.view.ResourceConverter
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.viewmodel.PostViewModel

class PostConverter(
        private val userService: QueryService<User>
) : ResourceConverter<PostViewModel, Post> {

    override val viewModelClass = PostViewModel::class
    override val resourceClass = Post::class

    override fun PostViewModel.toResource() = Post(
            owner = userService.findById(ownerId.toUUID()).get(),
            abstract = abstract,
            title = title,
            tags = tags.split(", ").map { it.trim() }.toSet(),
            content = content,
            excerpt = excerpt,
            publishedAt = DateTime.Companion.fromUnix(Long.MAX_VALUE)
    )

    override fun Post.toViewModel(context: OperationContext<out Any>) = PostViewModel(
            ownerId = owner.id.toString(),
            abstract = abstract,
            title = title,
            tags = tags.joinToString(", "),
            content = content,
            excerpt = excerpt,
            publicationDate = publishedAt.format(humanReadableFormat),
            isPublished = publishedAt < DateTime.now(),
            id = id.toString()
    )
}
