package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.SavesResource
import org.agorahq.agora.core.api.operation.types.Procedure
import org.agorahq.agora.core.api.operation.types.ProcedureDescriptor
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.agorahq.agora.post.viewmodel.PostViewModel

class CreatePost(
        private val postStorage: StorageService<Post>,
        private val converterService: ConverterService
) : Procedure<Post, PostViewModel>, ProcedureDescriptor<Post, PostViewModel> by Companion {

    override fun fetchResource(context: OperationContext<out PostViewModel>): ElementSource<Post> {
        return ElementSource.of(converterService.convertToResource<Post>(context.input).get())
    }

    override fun createCommand(
            context: OperationContext<out PostViewModel>,
            data: ElementSource<Post>
    ) = Command.of {
        postStorage.create(data.asSingle())
    }

    override fun toString() = name

    companion object : ProcedureDescriptor<Post, PostViewModel> {
        override val name = "Create post"
        override val attributes = Attributes.create<Post, PostViewModel, Unit>(
                route = ShowPostURL.root,
                urlClass = ShowPostURL::class,
                additionalAttributes = listOf(SavesResource)
        )
    }

}
