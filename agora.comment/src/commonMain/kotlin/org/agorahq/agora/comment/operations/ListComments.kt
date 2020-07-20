package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.renderCommentList
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ShowsPageElements
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.PageElementQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.hexworks.cobalt.core.api.UUID

class ListComments(
        private val commentService: PageElementQueryService<Comment>,
        private val converterService: ConverterService
) : ParameterizedRenderer<Comment, UUID>, ParameterizedRendererDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        return ElementSource.ofSequence(commentService
                .findByParentId(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = Command.of {
        Templates.htmlPartial {
            renderCommentList(
                    context = context,
                    model = CommentListViewModel(
                            comments = data.asSequence().map {
                                converterService.convertToView<CommentViewModel>(it, context).get()
                            })
            )
        }
    }

    companion object : ParameterizedRendererDescriptor<Comment, UUID> {
        override val name = "List comments"
        override val attributes = Attributes.create<Comment, UUID, String>(
                route = CommentURL.root,
                urlClass = CommentURL::class,
                additionalAttributes = listOf(ShowsPageElements)
        )
    }
}
