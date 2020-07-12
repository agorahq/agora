package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.ResourceSaver
import org.agorahq.agora.core.api.operation.SaveResource
import org.agorahq.agora.core.api.operation.SaveResourceDescriptor
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.viewmodel.PostViewModel

class EditPost(
        private val postStorage: StorageService<Post>,
        private val converterService: ConverterService
) : SaveResource<Post, PostViewModel>, SaveResourceDescriptor<Post, PostViewModel> by Companion {

    override fun ViewModelContext<PostViewModel>.fetchData(): ElementSource<Post> {
        return ElementSource.fromElement(converterService.convertToResource<Post>(viewModel).get())
    }

    override fun ViewModelContext<PostViewModel>.createCommand(data: ElementSource<Post>) = {
        postStorage.create(data.asSingle())
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : SaveResourceDescriptor<Post, PostViewModel> {

        override val name = "Edit Post"
        override val resourceClass = Post::class
        override val type = ResourceSaver(Post::class, PostViewModel::class)
        override val route = "${PostURL.root}/edit"
        override val urlClass = PostURL::class

        override fun toString() = OperationDescriptor.toString(this)
    }
}
