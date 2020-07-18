package org.agorahq.agora.post.operations

import kotlinx.html.a
import kotlinx.html.style
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ShowsResourceLink
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.post.domain.EditPostURL
import org.agorahq.agora.post.domain.Post
import org.hexworks.cobalt.core.api.UUID

class ShowEditPostLink(
        private val postQueryService: QueryService<Post>
) : ParameterizedRenderer<Post, UUID>, ParameterizedRendererDescriptor<Post, UUID> by Companion {


    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Post> {
        return ElementSource.ofMaybe(postQueryService.findById(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Post>
    ) = data.map { post ->
        Templates.htmlPartial {
            a(href = EditPostURL(post.id.toString()).generate(), classes = "btn btn-warning mr-2") {
                this.style = "display: inline-block; margin-bottom: 0;"
                +"Edit"
            }
        }
    }.toCommand()


    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Post, UUID> {
        override val name = "Show edit post link"
        override val attributes = Attributes.create<Post, UUID, String>(
                route = "", // TODO: this shouldn't be mandatory
                urlClass = EditPostURL::class, // TODO: this shouldn't be mandatory
                additionalAttributes = listOf(ShowsResourceLink(Post::class))
        )
    }
}
