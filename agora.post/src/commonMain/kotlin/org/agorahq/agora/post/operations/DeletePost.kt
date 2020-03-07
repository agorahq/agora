package org.agorahq.agora.post.operations

import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.DeleteResource
import org.agorahq.agora.core.api.operation.DeleteResourceDescriptor
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.security.OperationType.ResourceDeleter
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL

class DeletePost(
        private val postStorage: StorageService<Post>
) : DeleteResource<Post>, DeleteResourceDescriptor<Post> by Companion {

    override val descriptor = DeletePost

    override fun ResourceIdContext.createCommand() = {
        postStorage.deleteById(id)
    }.toCommand()

    companion object : DeleteResourceDescriptor<Post> {

        override val name = "Delete Post"
        override val resourceClass = Post::class
        override val type = ResourceDeleter(Post::class)
        override val route = PostURL.root
        override val urlClass = PostURL::class
    }
}