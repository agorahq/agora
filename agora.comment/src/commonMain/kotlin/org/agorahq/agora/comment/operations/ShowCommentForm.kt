package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.templates.renderCommentForm
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.PageElementFormRenderer
import org.agorahq.agora.core.api.shared.templates.Templates

class ShowCommentForm : ParameterizedRenderer<Comment, Page>, ParameterizedRendererDescriptor<Comment, Page> by Companion {

    override fun fetchResource(context: OperationContext<out Page>): ElementSource<Comment> {
        return ElementSource.fromElement(Comment(
                owner = context.user,
                content = "",
                parentId = context.input.id))
    }

    override fun createCommand(context: OperationContext<out Page>, data: ElementSource<Comment>) = {
        val comment = data.asSingle()
        Templates.htmlPartial {
            renderCommentForm(CommentViewModel(
                    parentId = comment.parentId.toString(),
                    content = comment.content,
                    username = comment.owner.username,
                    userId = comment.owner.id.toString()
            ))
        }
    }.toCommand()

    companion object : ParameterizedRendererDescriptor<Comment, Page> by OperationDescriptor.create(
            name = "Show Comment Form",
            route = CommentURL.root,
            urlClass = CommentURL::class,
            facets = listOf(PageElementFormRenderer)
    )
}
