package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.SaveResource
import org.agorahq.agora.core.api.operation.SaveResourceDescriptor
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.security.OperationType.ResourceSaver
import org.agorahq.agora.core.api.service.StorageService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.viewmodel.PostViewModel

class CreatePost(
        private val postStorage: StorageService<Post>,
        private val converterService: ConverterService
) : SaveResource<Post, PostViewModel>, SaveResourceDescriptor<Post, PostViewModel> by Companion {

    override val descriptor = CreatePost

    override fun ViewModelContext<PostViewModel>.createCommand() = {
        postStorage.create(converterService.convertToResource<Post>(viewModel).get())
    }.toCommand()

    companion object : SaveResourceDescriptor<Post, PostViewModel> {

        override val name = "Create Post"
        override val resourceClass = Post::class
        override val type = ResourceSaver(Post::class, PostViewModel::class)
        override val route = PostURL.root
        override val urlClass = PostURL::class
    }

}