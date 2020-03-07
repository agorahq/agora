package org.agorahq.agora.post.operations

import org.agorahq.agora.comment.operations.ListComments
import org.agorahq.agora.core.api.extensions.renderPageElementFormsFor
import org.agorahq.agora.core.api.extensions.renderPageElementListsFor
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.RenderResource
import org.agorahq.agora.core.api.operation.RenderResourceDescriptor
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.security.OperationType.PageRenderer
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.templates.POST_DETAILS
import org.agorahq.agora.post.viewmodel.PostViewModel

@Suppress("UNCHECKED_CAST")
class ShowPost(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : RenderResource<Post>, RenderResourceDescriptor<Post> by Companion {

    override val descriptor = ShowPost

    override fun PageURLContext<Post>.createCommand() = {
        val post = postQueryService.findByUrl(url).get()
        val model = converterService.convertToView<PostViewModel>(post, this).get()
        val renderedPageElements = StringBuilder()
        renderedPageElements.append(renderPageElementListsFor(post))
        renderedPageElements.append(renderPageElementFormsFor(post))
        POST_DETAILS.render(model.copy(renderedPageElements = renderedPageElements.toString()))
    }.toCommand()

    companion object : RenderResourceDescriptor<Post> {

        override val name = "Show Post"
        override val resourceClass = Post::class
        override val type = PageRenderer(Post::class)
        override val route = PostURL.route
        override val urlClass = PostURL::class

    }

}