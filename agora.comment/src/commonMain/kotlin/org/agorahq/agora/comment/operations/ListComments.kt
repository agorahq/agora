package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.renderCommentList
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Facet
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.PageElementListRenderer
import org.agorahq.agora.core.api.operation.RenderPageElementList
import org.agorahq.agora.core.api.operation.RenderPageElementListDescriptor
import org.agorahq.agora.core.api.operation.context.PageContext
import org.agorahq.agora.core.api.service.PageElementQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService

class ListComments(
        private val commentService: PageElementQueryService<Comment>,
        private val converterService: ConverterService
) : RenderPageElementList<Comment, Page>, RenderPageElementListDescriptor<Comment, Page> by Companion {

    override fun fetchResource(context: PageContext<Page>): ElementSource<Comment> {
        return ElementSource.fromSequence(commentService
                .findByParent(context.page))
    }

    override fun createCommand(context: PageContext<Page>, data: ElementSource<Comment>) = {
        Templates.htmlPartial {
            renderCommentList(CommentListViewModel(
                    comments = data.asSequence().map {
                        converterService.convertToView<CommentViewModel>(it, context).get()
                    },
                    context = context))
        }
    }.toCommand()

    companion object : RenderPageElementListDescriptor<Comment, Page> {

        override val name = "List Comments"
        override val resourceClass = Comment::class
        override val type = PageElementListRenderer(Comment::class, Page::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
        override val facets = listOf<Facet>()

        override fun toString() = OperationDescriptor.toString(this)
    }
}
