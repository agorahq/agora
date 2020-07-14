package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.renderCommentList
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.PageElementListRenderer
import org.agorahq.agora.core.api.service.PageElementQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService

class ListComments(
        private val commentService: PageElementQueryService<Comment>,
        private val converterService: ConverterService
) : ParameterizedRenderer<Comment, Page>, ParameterizedRendererDescriptor<Comment, Page> by Companion {

    override fun fetchResource(context: OperationContext<out Page>): ElementSource<Comment> {
        return ElementSource.fromSequence(commentService
                .findByParent(context.input))
    }

    override fun createCommand(context: OperationContext<out Page>, data: ElementSource<Comment>) = {
        Templates.htmlPartial {
            renderCommentList(CommentListViewModel(
                    comments = data.asSequence().map {
                        converterService.convertToView<CommentViewModel>(it, context).get()
                    }))
        }
    }.toCommand()

    companion object : ParameterizedRendererDescriptor<Comment, Page> by OperationDescriptor.create(
            name = "List Comments",
            route = CommentURL.root,
            urlClass = CommentURL::class,
            facets = listOf(PageElementListRenderer)
    )
}
