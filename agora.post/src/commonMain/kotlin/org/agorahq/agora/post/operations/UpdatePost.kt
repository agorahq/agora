package org.agorahq.agora.post.operations

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.UpdatePostURL
import org.agorahq.agora.post.viewmodel.PostViewModel

class UpdatePost(
        private val postQueryService: QueryService<Post>,
        private val postStorage: StorageService<Post>
) : Procedure<Post, PostViewModel>, ProcedureDescriptor<Post, PostViewModel> by Companion {

    override fun fetchResource(context: OperationContext<out PostViewModel>): ElementSource<Post> {
        val model = context.input
        return ElementSource.ofMaybe(postQueryService.findById(model.id.toUUID()).map { post ->
            post.copy(
                    updatedAt = DateTime.now(),
                    publishedAt = DateTime.now(),
                    abstract = model.abstract,
                    title = model.title,
                    tags = model.tags.split(", ").map { it.trim() }.toSet(),
                    content = model.content,
                    excerpt = model.excerpt
            )
        })
    }

    override fun createCommand(
            context: OperationContext<out PostViewModel>,
            data: ElementSource<Post>
    ) = Command.of {
        data.map { postStorage.update(it) }
        Unit
    }

    companion object : ProcedureDescriptor<Post, PostViewModel> {
        override val name = "Update comment"
        override val attributes = Attributes.create<Post, PostViewModel, Unit>(
                route = UpdatePostURL.route,
                urlClass = UpdatePostURL::class,
                additionalAttributes = listOf()
        )
    }
}
