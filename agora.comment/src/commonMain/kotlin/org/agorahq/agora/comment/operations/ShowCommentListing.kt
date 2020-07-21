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
import org.agorahq.agora.core.api.service.MarkdownRenderer
import org.agorahq.agora.core.api.service.PageElementQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.hexworks.cobalt.core.api.UUID

class ShowCommentListing(
        private val commentQueryService: PageElementQueryService<Comment>,
        private val converterService: ConverterService,
        private val markdownRenderer: MarkdownRenderer
) : ParameterizedRenderer<Comment, UUID>, ParameterizedRendererDescriptor<Comment, UUID> by Companion {

    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Comment> {
        return ElementSource.ofSequence(commentQueryService
                .findByParentId(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Comment>
    ) = Command.of {
        Templates.htmlPartial {
            renderCommentList(
                    context = context,
                    parentId = context.input,
                    model = CommentListViewModel(
                            comments = data.asSequence().map {
                                val comment = converterService.convertToView<CommentViewModel>(it, context).get()
                                comment.copy(content = markdownRenderer.render(comment.content))
                            })
            )
        }
    }

    companion object : ParameterizedRendererDescriptor<Comment, UUID> {
        override val name = "Show comment listing"
        override val attributes = Attributes.create<Comment, UUID, String>(
                route = CommentURL.root,
                urlClass = CommentURL::class,
                additionalAttributes = listOf(ShowsPageElements)
        )
    }
}
