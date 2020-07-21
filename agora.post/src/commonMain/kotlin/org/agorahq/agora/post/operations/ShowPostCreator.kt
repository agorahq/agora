package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.types.Renderer
import org.agorahq.agora.core.api.operation.types.RendererDescriptor
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.EditPostURL
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.agorahq.agora.post.domain.UpdatePostURL
import org.agorahq.agora.post.templates.renderPostEditor
import org.agorahq.agora.post.viewmodel.PostViewModel

class ShowPostCreator(
        private val converterService: ConverterService
) : Renderer<Post>, RendererDescriptor<Post> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>) =
            ElementSource.of(Post.empty(context.user))

    override fun createCommand(context: OperationContext<out Unit>, data: ElementSource<Post>) = Command.of {
        val model = converterService.convertToView<PostViewModel>(data.asSingle(), context).get()
        Templates.htmlTemplate {
            renderPostEditor(
                    path = ShowPostURL.root,
                    post = model,
                    context = context
            )
        }
    }

    override fun toString() = name

    companion object : RendererDescriptor<Post> {
        override val name = "Create and edit new post"
        override val attributes = Attributes.create<Post, Unit, String>(
                route = "${ShowPostURL.root}/create",
                urlClass = ShowPostURL::class // TODO: shouldn't be mandatory!
        )
    }
}
