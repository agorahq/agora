package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.Procedure
import org.agorahq.agora.core.api.operation.ProcedureDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ResourceSaver
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.viewmodel.PostViewModel

class EditPost(
        private val postStorage: StorageService<Post>,
        private val converterService: ConverterService
) : Procedure<Post, PostViewModel>, ProcedureDescriptor<Post, PostViewModel> by Companion {

    override fun fetchResource(context: OperationContext<out PostViewModel>): ElementSource<Post> {
        return ElementSource.fromElement(converterService.convertToResource<Post>(context.input).get())
    }

    override fun createCommand(context: OperationContext<out PostViewModel>, data: ElementSource<Post>) = {
        postStorage.create(data.asSingle())
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : ProcedureDescriptor<Post, PostViewModel> by OperationDescriptor.create(
            name = "Edit Post",
            route = "${PostURL.root}/edit",
            urlClass = PostURL::class,
            facets = listOf(ResourceSaver)
    )
}
