package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.DeleteResource
import org.agorahq.agora.core.api.operation.DeleteResourceDescriptor
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.ResourceDeleter
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.viewmodel.PostViewModel

class DeletePost(
        private val postQueryService: QueryService<Post>,
        private val postStorage: StorageService<Post>
) : DeleteResource<Post>, DeleteResourceDescriptor<Post> by Companion {


    override fun ResourceIdContext.fetchData(): ElementSource<Post> {
        return ElementSource.fromMaybe(postQueryService.findById(id))
    }

    override fun ResourceIdContext.createCommand(data: ElementSource<Post>) = {
        postStorage.delete(data.asSingle())
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : DeleteResourceDescriptor<Post> {

        override val name = "Delete Post"
        override val resourceClass = Post::class
        override val type = ResourceDeleter(Post::class)
        override val route = "${PostURL.root}/delete"
        override val urlClass = PostURL::class

        override fun toString() = OperationDescriptor.toString(this)
    }
}
