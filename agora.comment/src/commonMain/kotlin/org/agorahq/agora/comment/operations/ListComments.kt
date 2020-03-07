package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.COMMENT_LIST
import org.agorahq.agora.comment.viewmodel.CommentListViewModel
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.RenderPageElementList
import org.agorahq.agora.core.api.operation.RenderPageElementListDescriptor
import org.agorahq.agora.core.api.operation.context.PageContext
import org.agorahq.agora.core.api.security.OperationType.PageElementListRenderer
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.service.PageElementQueryService
import org.agorahq.agora.core.api.view.ConverterService

class ListComments(
        private val commentService: PageElementQueryService<Comment>,
        private val converterService: ConverterService
) : RenderPageElementList<Comment, Page>, RenderPageElementListDescriptor<Comment> by Companion {

    override val descriptor = ListComments

    override fun PageContext<Page>.createCommand() = {
        COMMENT_LIST.render(CommentListViewModel(
                comments = commentService
                        .findByParent(page)
                        .map {
                            converterService.convertToView<CommentViewModel>(it, this).get()
                        },
                owner = User.ANONYMOUS))
    }.toCommand()

    companion object : RenderPageElementListDescriptor<Comment> {

        override val name = "List Comments"
        override val resourceClass = Comment::class
        override val type = PageElementListRenderer(Comment::class, Page::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
    }

}
