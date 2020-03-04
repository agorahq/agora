package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.RenderPageList
import org.agorahq.agora.core.api.operation.RenderPageListDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.OperationType.PageListRenderer
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.templates.POST_LIST
import org.agorahq.agora.post.viewmodel.PostListViewModel
import org.agorahq.agora.post.viewmodel.PostViewModel

class ListPosts(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : RenderPageList<Post>, RenderPageListDescriptor<Post> by Companion {

    override fun OperationContext.createCommand() = {
        POST_LIST.render(PostListViewModel(
                posts = postQueryService.findAll().map {
                    converterService.convertToView<PostViewModel>(it, this).get()
                },
                context = this))
    }.toCommand()

    companion object : RenderPageListDescriptor<Post> {

        override val name = "List Posts"
        override val resourceClass = Post::class
        override val type = PageListRenderer(Post::class)
        override val route = PostURL.root
        override val urlClass = PostURL::class

    }

}