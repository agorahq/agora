package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.hexworks.cobalt.core.api.UUID

class DeletePost(
        private val postQueryService: QueryService<Post>,
        private val postStorage: StorageService<Post>
) : Procedure<Post, UUID>, ProcedureDescriptor<Post, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Post> {
        return ElementSource.ofMaybe(postQueryService.findById(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Post>
    ) = Command.of {
        postStorage.delete(data.asSingle())
    }

    override fun toString() = name

    companion object : ProcedureDescriptor<Post, UUID> {
        override val name = "Delete Post"
        override val attributes = Attributes.create<Post, UUID, Unit>(
                route = "${ShowPostURL.root}/delete",
                urlClass = ShowPostURL::class
        )
    }
}
