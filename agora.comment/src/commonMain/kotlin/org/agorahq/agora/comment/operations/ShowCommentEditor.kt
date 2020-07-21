package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.UpdateCommentURL
import org.agorahq.agora.comment.templates.renderCommentEditor
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.RendersForm
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.hexworks.cobalt.core.api.UUID

class ShowCommentEditor(
        private val commentQueryService: QueryService<Comment>
) : ParameterizedRenderer<Comment, UUID>, ParameterizedRendererDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        return ElementSource.ofMaybe(commentQueryService.findById(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = Command.of {
        val comment = data.asSingle()
        Templates.htmlPartial {
            renderCommentEditor(comment = CommentViewModel(
                    id = comment.id.toString(),
                    username = comment.owner.username,
                    content = comment.content,
                    parentId = comment.parentId.toString()
            ), path = UpdateCommentURL(comment.id).generate())
        }
    }

    companion object : ParameterizedRendererDescriptor<Comment, UUID> {
        override val name = "Show comment editor"
        override val attributes = Attributes.create<Comment, UUID, String>(
                route = UpdateCommentURL.route, // TODO: no good!
                urlClass = UpdateCommentURL::class,
                additionalAttributes = listOf(RendersForm(Comment::class))
        )

    }
}
