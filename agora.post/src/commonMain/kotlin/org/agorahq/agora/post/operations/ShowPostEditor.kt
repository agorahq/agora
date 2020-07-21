package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toUUID
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
import org.agorahq.agora.post.domain.UpdatePostURL
import org.agorahq.agora.post.templates.renderPostEditor
import org.agorahq.agora.post.viewmodel.PostViewModel

class ShowPostEditor(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : ParameterizedRenderer<Post, EditPostURL>, ParameterizedRendererDescriptor<Post, EditPostURL> by Companion {

    override fun fetchResource(context: OperationContext<out EditPostURL>): ElementSource<Post> {
        return ElementSource.of(postQueryService
                .findById(context.input.id)
                .orElse(Post.empty(context.user)))
    }

    override fun createCommand(
            context: OperationContext<out EditPostURL>,
            data: ElementSource<Post>
    ) = Command.of {
        val model = converterService.convertToView<PostViewModel>(data.asSingle(), context).get()
        Templates.htmlTemplate {
            renderPostEditor(
                    path = UpdatePostURL(model.id.toUUID()).generate(),
                    post = model,
                    context = context
            )
        }
    }

    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Post, EditPostURL> {
        override val name = "Show post editor"
        override val attributes = Attributes.create<Post, EditPostURL, String>(
                route = EditPostURL.route,
                urlClass = EditPostURL::class
        )
    }
}
