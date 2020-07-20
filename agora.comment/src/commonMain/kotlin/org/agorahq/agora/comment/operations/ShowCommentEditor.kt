package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.EditCommentURL
import org.agorahq.agora.comment.templates.renderCommentForm
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.SavesResource
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.templates.Templates

class ShowCommentEditor(
        private val commentQueryService: QueryService<Comment>
) : ParameterizedRenderer<Comment, EditCommentURL>, ParameterizedRendererDescriptor<Comment, EditCommentURL> by Companion {

    override fun fetchResource(context: OperationContext<out EditCommentURL>): ElementSource<Comment> {
        return ElementSource.ofMaybe(commentQueryService.findById(context.input.id.toUUID()))
    }

    override fun createCommand(
            context: OperationContext<out EditCommentURL>,
            data: ElementSource<Comment>
    ) = Command.of {
        val comment = data.asSingle()
        Templates.htmlPartial {
            renderCommentForm(CommentViewModel(
                    id = data.asSingle().id.toString(),
                    parentId = comment.parentId.toString(),
                    content = comment.content,
                    username = comment.owner.username,
                    userId = comment.owner.id.toString()
            ))
        }
    }

    companion object : ParameterizedRendererDescriptor<Comment, EditCommentURL> {
        override val name = "Show comment editor"
        override val attributes = Attributes.create<Comment, EditCommentURL, String>(
                route = EditCommentURL.route,
                urlClass = EditCommentURL::class,
                additionalAttributes = listOf(SavesResource)
        )

    }
}
