package org.agorahq.agora.post.operations

import kotlinx.html.*
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ShowsResourceLink
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.hexworks.cobalt.core.api.UUID

class ShowDeletePostLink(
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
            form(DeletePost.route, method = FormMethod.post) {
                this.style = "display: inline-block; margin-bottom: 0;"
                input(type = InputType.hidden, name = "id") {
                    value = post.id.toString()
                }
                button(type = ButtonType.submit, classes = "btn btn-danger") {
                    +"Delete"
                }
            }
        }
    }.toCommand()

    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Post, UUID> {
        override val name = "Show delete post link"
        override val attributes = Attributes.create<Post, UUID, String>(
                route = "", // TODO: this shouldn't be mandatory
                urlClass = ShowPostURL::class, // TODO: this shouldn't be mandatory
                additionalAttributes = listOf(ShowsResourceLink(Post::class))
        )
    }
}
