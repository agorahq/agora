package org.agorahq.agora.post.operations

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.*
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.extensions.isPublished

class TogglePostPublished(
        private val postQueryService: QueryService<Post>,
        private val postStorage: StorageService<Post>
) : AlterResource<Post>, AlterResourceDescriptor<Post> by Companion {


    override fun fetchResource(context: ResourceIdContext): ElementSource<Post> {
        return ElementSource.fromMaybe(postQueryService.findById(context.id))
    }

    override fun createCommand(context: ResourceIdContext, data: ElementSource<Post>) = {
        val post = data.asSingle()
        postStorage.update(post.copy(
                publishedAt = if (post.isPublished) {
                    DateTime.fromUnix(Long.MAX_VALUE)
                } else {
                    DateTime.now()
                },
                updatedAt = DateTime.now()))
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : AlterResourceDescriptor<Post> {

        override val name = "Toggle Post Published"
        override val resourceClass = Post::class
        override val type = OperationType.ResourceAlterer(Post::class)
        override val route = "${PostURL.root}/toggle-post-published"
        override val urlClass = PostURL::class
        override val facets = listOf<Facet>()

        override fun toString() = OperationDescriptor.toString(this)
    }
}
