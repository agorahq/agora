package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.renderCommentCreator
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.RendersPageElementForm
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.shared.templates.Templates
import org.hexworks.cobalt.core.api.UUID

class ShowCommentCreator :
        ParameterizedRenderer<Comment, UUID>, ParameterizedRendererDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>) =
            ElementSource.of(Comment.empty(
                    owner = context.user,
                    parentId = context.input
            ))

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = Command.of {
        val comment = data.asSingle()
        Templates.htmlPartial {
            renderCommentCreator(comment = CommentViewModel(
                    id = data.asSingle().id.toString(),
                    parentId = comment.parentId.toString(),
                    content = comment.content,
                    username = comment.owner.username,
                    ownerId = comment.owner.id.toString()
            ), path = CreateComment.route)
        }
    }

    companion object : ParameterizedRendererDescriptor<Comment, UUID> {
        override val name = "Show comment editor"
        override val attributes = Attributes.create<Comment, UUID, String>(
                route = CommentURL.route, // TODO: no good!
                urlClass = CommentURL::class,
                additionalAttributes = listOf(RendersPageElementForm(Comment::class))
        )

    }
}
