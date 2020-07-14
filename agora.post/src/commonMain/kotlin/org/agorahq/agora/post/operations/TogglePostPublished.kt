package org.agorahq.agora.post.operations

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.Procedure
import org.agorahq.agora.core.api.operation.ProcedureDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ResourceAlterer
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.extensions.isPublished
import org.hexworks.cobalt.core.api.UUID

class TogglePostPublished(
        private val postQueryService: QueryService<Post>,
        private val postStorage: StorageService<Post>
) : Procedure<Post, UUID>, ProcedureDescriptor<Post, UUID> by Companion {


    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Post> {
        return ElementSource.fromMaybe(postQueryService.findById(context.input))
    }

    override fun createCommand(context: OperationContext<out UUID>, data: ElementSource<Post>) = {
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

    companion object : ProcedureDescriptor<Post, UUID> by OperationDescriptor.create(
            name = "Toggle Post Published",
            route = "${PostURL.root}/toggle-post-published",
            urlClass = PostURL::class,
            facets = listOf(ResourceAlterer)
    )
}
