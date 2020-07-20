package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.PageRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.agorahq.agora.post.templates.renderPostDetails
import org.agorahq.agora.post.viewmodel.PostViewModel

@Suppress("UNCHECKED_CAST")
class ShowPost(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : ParameterizedRenderer<Post, ShowPostURL>, ParameterizedRendererDescriptor<Post, ShowPostURL> by Companion {

    override fun fetchResource(context: OperationContext<out ShowPostURL>): ElementSource<Post> {
        return ElementSource.of(postQueryService.findByUrl(context.input).get())
    }

    override fun createCommand(
            context: OperationContext<out ShowPostURL>,
            data: ElementSource<Post>
    ) = Command.of {
        val post = data.asSingle()
        val model = converterService.convertToView<PostViewModel>(post, context).get()
        Templates.htmlTemplate {
            renderPostDetails(
                    post = model,
                    ctx = context
            )
        }
    }

    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Post, ShowPostURL> {
        override val name = "Show post"
        override val attributes = Attributes.create<Post, ShowPostURL, String>(
                route = ShowPostURL.route,
                urlClass = ShowPostURL::class,
                additionalAttributes = listOf(PageRenderer)
        )
    }
}
