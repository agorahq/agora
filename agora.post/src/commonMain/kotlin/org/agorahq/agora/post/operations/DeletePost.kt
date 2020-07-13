package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.AlterResource
import org.agorahq.agora.core.api.operation.AlterResourceDescriptor
import org.agorahq.agora.core.api.operation.Facet
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.ResourceAlterer
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL

class DeletePost(
        private val postQueryService: QueryService<Post>,
        private val postStorage: StorageService<Post>
) : AlterResource<Post>, AlterResourceDescriptor<Post> by Companion {


    override fun fetchResource(context: ResourceIdContext): ElementSource<Post> {
        return ElementSource.fromMaybe(postQueryService.findById(context.id))
    }

    override fun createCommand(context: ResourceIdContext, data: ElementSource<Post>) = {
        postStorage.delete(data.asSingle())
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : AlterResourceDescriptor<Post> {

        override val name = "Delete Post"
        override val resourceClass = Post::class
        override val type = ResourceAlterer(Post::class)
        override val route = "${PostURL.root}/delete"
        override val urlClass = PostURL::class
        override val facets = listOf<Facet>()

        override fun toString() = OperationDescriptor.toString(this)
    }
}
