package org.agorahq.agora.post.operations

import kotlinx.html.a
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ShowsResourceCreatorLink
import org.agorahq.agora.core.api.operation.types.Renderer
import org.agorahq.agora.core.api.operation.types.RendererDescriptor
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL

class ShowCreatePostLink : Renderer<Post>, RendererDescriptor<Post> by Companion {

    override fun fetchResource(context: OperationContext<out Unit>) =
            ElementSource.of(Post.empty(context.user))

    override fun createCommand(
            context: OperationContext<out Unit>,
            data: ElementSource<Post>
    ) = Command.of {
        Templates.htmlPartial {
            a(ShowPostCreator.route, classes = "btn btn-primary") {
                +"Create New Post"
            }
        }
    }

    override fun toString() = name

    companion object : RendererDescriptor<Post> {
        override val name = "Show create post link"
        override val attributes = Attributes.create<Post, Unit, String>(
                route = ShowPostCreator.route,
                urlClass = ShowPostURL::class, // TODO: this shouldn't be mandatory
                additionalAttributes = listOf(ShowsResourceCreatorLink(Post::class))
        )
    }
}
