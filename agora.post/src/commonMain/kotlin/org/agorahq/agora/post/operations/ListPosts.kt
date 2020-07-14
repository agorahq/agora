package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.Renderer
import org.agorahq.agora.core.api.operation.RendererDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.PageListRenderer
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
) : Renderer<Post>, RendererDescriptor<Post> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>): ElementSource<Post> {
        return ElementSource.fromSequence(postQueryService.findAll())
    }

    override fun createCommand(context: OperationContext<out Unit>, data: ElementSource<Post>) = {
        Templates.htmlTemplate {
            renderPostList(context, PostListViewModel(
                    posts = data.asSequence().sortedByDescending { it.publishedAt }.map {
                        converterService.convertToView<PostViewModel>(it, context).get()
                    }
            ))
        }
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : RendererDescriptor<Post> by OperationDescriptor.create(
            name = "List Posts",
            route = PostURL.root,
            urlClass = PostURL::class,
            facets = listOf(PageListRenderer)
    )
}
