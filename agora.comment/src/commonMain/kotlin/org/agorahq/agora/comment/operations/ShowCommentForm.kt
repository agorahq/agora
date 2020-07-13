package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.renderCommentForm
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Facet
import org.agorahq.agora.core.api.operation.OperationType.PageElementFormRenderer
import org.agorahq.agora.core.api.operation.RenderPageElementForm
import org.agorahq.agora.core.api.operation.RenderPageElementFormDescriptor
import org.agorahq.agora.core.api.operation.context.PageContext
import org.agorahq.agora.core.api.shared.templates.Templates

class ShowCommentForm : RenderPageElementForm<Comment, Page>, RenderPageElementFormDescriptor<Comment, Page> by Companion {

    override fun fetchResource(context: PageContext<Page>): ElementSource<Comment> {
        return ElementSource.fromElement(Comment(
                owner = context.user,
                content = "",
                parentId = context.page.id))
    }

    override fun createCommand(context: PageContext<Page>, data: ElementSource<Comment>) = {
        val comment = data.asSingle()
        Templates.htmlPartial {
            renderCommentForm(CommentViewModel(
                    parentId = comment.parentId.toString(),
                    content = comment.content,
                    username = comment.owner.username,
                    userId = comment.owner.id.toString(),
                    context = context
            ))
        }
    }.toCommand()

    companion object : RenderPageElementFormDescriptor<Comment, Page> {
        override val name = "Show Comment Form"
        override val resourceClass = Comment::class
        override val type = PageElementFormRenderer(Comment::class, Page::class)
        override val route = CommentURL.root
        override val urlClass = CommentURL::class
        override val facets = listOf<Facet>()
    }
}
