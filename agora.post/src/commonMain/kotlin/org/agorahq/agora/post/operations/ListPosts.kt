package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.PageListRenderer
import org.agorahq.agora.core.api.operation.RenderPageList
import org.agorahq.agora.core.api.operation.RenderPageListDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.templates.renderPostList
import org.agorahq.agora.post.viewmodel.PostListViewModel
import org.agorahq.agora.post.viewmodel.PostViewModel

class ListPosts(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : RenderPageList<Post>, RenderPageListDescriptor<Post> by Companion {

    override fun OperationContext.fetchData(): ElementSource<Post> {
        return ElementSource.fromSequence(postQueryService.findAll())
    }

    override fun OperationContext.createCommand(data: ElementSource<Post>) = {
        val ctx = this
        Templates.htmlTemplate {
            renderPostList(PostListViewModel(
                    posts = data.asSequence().sortedByDescending { it.publishedAt }.map {
                        converterService.convertToView<PostViewModel>(it, ctx).get()
                    },
                    context = ctx
            ))
        }
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : RenderPageListDescriptor<Post> {

        override val name = "List Posts"
        override val resourceClass = Post::class
        override val type = PageListRenderer(Post::class)
        override val route = PostURL.root
        override val urlClass = PostURL::class

        override fun toString() = OperationDescriptor.toString(this)
    }
}
