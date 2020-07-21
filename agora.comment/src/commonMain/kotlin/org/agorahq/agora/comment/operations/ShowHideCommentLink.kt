package org.agorahq.agora.comment.operations

import kotlinx.html.*
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.extensions.isPublished
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

class ShowHideCommentLink(
        private val commentQueryService: QueryService<Comment>
) : ParameterizedRenderer<Comment, UUID>, ParameterizedRendererDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        return ElementSource.ofMaybe(commentQueryService.findById(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = data.map { comment ->
        val isPublished = comment.isPublished
        Templates.htmlPartial {
            form(ToggleCommentPublished.route, method = FormMethod.post) {
                this.style = "display: inline-block; margin-bottom: 0;"
                input(type = InputType.hidden, name = "id") {
                    value = comment.id.toString()
                }
                div("btn-group-toggle mr-2") {
                    button(
                            type = ButtonType.submit,
                            classes = "btn btn-primary ${if (isPublished) "active" else ""}"
                    ) {
                        attributes["data-toggle"] = "button"
                        attributes["aria-pressed"] = isPublished.toString()
                        if (isPublished) {
                            +"Hide"
                        } else {
                            +"Publish"
                        }
                    }
                }
            }
        }
    }.toCommand()


    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Comment, UUID> {
        override val name = "Show hide comment link"
        override val attributes = Attributes.create<Comment, UUID, String>(
                route = ToggleCommentPublished.route, // TODO: this shouldn't be mandatory
                urlClass = CommentURL::class, // TODO: this shouldn't be mandatory
                additionalAttributes = listOf(ShowsResourceLink(Comment::class))
        )

    }
}
