package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ListsResources
import org.agorahq.agora.core.api.operation.types.Renderer
import org.agorahq.agora.core.api.operation.types.RendererDescriptor
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.agorahq.agora.post.templates.renderPostList
import org.agorahq.agora.post.viewmodel.PostListViewModel
import org.agorahq.agora.post.viewmodel.PostViewModel

class ShowPostListing(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : Renderer<Post>, RendererDescriptor<Post> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>): ElementSource<Post> {
        return ElementSource.ofSequence(postQueryService.findAll())
    }

    override fun createCommand(
            context: OperationContext<out Unit>,
            data: ElementSource<Post>
    ) = Command.of {
        Templates.htmlTemplate {
            renderPostList(context, PostListViewModel(
                    posts = data.asSequence().sortedByDescending { it.publishedAt }.map {
                        converterService.convertToView<PostViewModel>(it, context).get()
                    }
            ))
        }
    }

    override fun toString() = name

    companion object : RendererDescriptor<Post> {

        override val name = "Show post listing"

        override val attributes = Attributes.create<Post, Unit, String>(
                route = ShowPostURL.root,
                urlClass = ShowPostURL::class,
                additionalAttributes = listOf(ListsResources)
        )
    }
}
