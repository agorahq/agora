package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.ResourceId
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.EditPostURL
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.renderPostEditor
import org.agorahq.agora.post.viewmodel.PostViewModel

class ShowPostEditor(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : ParameterizedRenderer<Post, ResourceId>, ParameterizedRendererDescriptor<Post, ResourceId> by Companion {

    override fun fetchResource(context: OperationContext<out ResourceId>): ElementSource<Post> {
        return ElementSource.of(postQueryService.findById(context.input.id).get())
    }

    override fun createCommand(
            context: OperationContext<out ResourceId>,
            data: ElementSource<Post>
    ) = Command.of {
        val model = converterService.convertToView<PostViewModel>(data.asSingle(), context).get()
        Templates.htmlTemplate {
            renderPostEditor(
                    post = model,
                    context = context
            )
        }
    }

    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Post, ResourceId> {
        override val name = "Show post editor"
        override val attributes = Attributes.create<Post, ResourceId, String>(
                route = EditPostURL.route,
                urlClass = EditPostURL::class
        )
    }
}
