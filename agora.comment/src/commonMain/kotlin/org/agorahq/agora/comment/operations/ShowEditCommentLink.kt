package org.agorahq.agora.comment.operations

import kotlinx.html.a
import kotlinx.html.style
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.EditCommentURL
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ShowsResourceLink
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.hexworks.cobalt.core.api.UUID

class ShowEditCommentLink(
        private val commentQueryService: QueryService<Comment>
) : ParameterizedRenderer<Comment, UUID>, ParameterizedRendererDescriptor<Comment, UUID> by Companion {


    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        return ElementSource.ofMaybe(commentQueryService.findById(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = data.map { comment ->
        Templates.htmlPartial {
            a(
                    href = "${context.currentPath}?pageElementToEdit=${comment.id}",
                    classes = "btn btn-warning mr-2"
            ) {
                this.style = "display: inline-block; margin-bottom: 0;"
                +"Edit"
            }
        }
    }.toCommand()


    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Comment, UUID> {
        override val name = "Show edit comment link"
        override val attributes = Attributes.create<Comment, UUID, String>(
                route = "", // TODO: this shouldn't be mandatory
                urlClass = EditCommentURL::class, // TODO: this shouldn't be mandatory
                additionalAttributes = listOf(ShowsResourceLink(Comment::class))
        )

    }
}
