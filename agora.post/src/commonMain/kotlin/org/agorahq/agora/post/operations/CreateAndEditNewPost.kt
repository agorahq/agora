package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.ResourceId
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.SavesResource
import org.agorahq.agora.core.api.operation.types.Renderer
import org.agorahq.agora.core.api.operation.types.RendererDescriptor
import org.agorahq.agora.post.domain.EditPostURL
import org.agorahq.agora.post.domain.Post

class CreateAndEditNewPost(
        private val showPostEditor: ShowPostEditor
) : Renderer<Post>, RendererDescriptor<Post> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>) =
            ElementSource.of(Post.empty(context.user))

    override fun createCommand(context: OperationContext<out Unit>, data: ElementSource<Post>) =
            showPostEditor.createCommand(
                    context = context.withInput(ResourceId(data.asSingle().id)),
                    data = data)

    override fun toString() = name

    companion object : RendererDescriptor<Post> {
        override val name = "Create and edit new post"
        override val attributes = Attributes.create<Post, Unit, String>(
                route = "/post/create",
                urlClass = EditPostURL::class, // TODO: shouldn't be mandatory!
                additionalAttributes = listOf(SavesResource)
        )
    }
}
