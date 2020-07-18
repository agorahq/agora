package org.agorahq.agora.post.operations

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.DeletesResource
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.agorahq.agora.post.extensions.isPublished
import org.hexworks.cobalt.core.api.UUID

class TogglePostPublished(
        private val postQueryService: QueryService<Post>,
        private val postStorage: StorageService<Post>
) : Procedure<Post, UUID>, ProcedureDescriptor<Post, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Post> {
        return ElementSource.ofMaybe(postQueryService.findById(context.input))
    }

    override fun createCommand(context: OperationContext<out UUID>, data: ElementSource<Post>) = Command.of {
        val post = data.asSingle()
        postStorage.update(post.copy(
                publishedAt = if (post.isPublished) {
                    DateTime.fromUnix(Long.MAX_VALUE)
                } else {
                    DateTime.now()
                },
                updatedAt = DateTime.now()))
    }

    override fun toString() = name

    companion object : ProcedureDescriptor<Post, UUID> {

        override val name = "Toggle post published"

        override val attributes = Attributes.create<Post, UUID, Unit>(
                route = "${ShowPostURL.root}/toggle-post-published",
                urlClass = ShowPostURL::class,
                additionalAttributes = listOf(DeletesResource(Post::class))
        )
    }
}
