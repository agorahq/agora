package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.renderPageElementFormsFor
import org.agorahq.agora.core.api.extensions.renderPageElementListsFor
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.OperationType.PageRenderer
import org.agorahq.agora.core.api.operation.RenderResource
import org.agorahq.agora.core.api.operation.RenderResourceDescriptor
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.templates.renderPostDetails
import org.agorahq.agora.post.viewmodel.PostViewModel

@Suppress("UNCHECKED_CAST")
class ShowPost(
        private val postQueryService: PageQueryService<Post>,
        private val converterService: ConverterService
) : RenderResource<Post>, RenderResourceDescriptor<Post> by Companion {

    override fun PageURLContext<Post>.fetchData(): ElementSource<Post> {
        return ElementSource.fromElement(postQueryService.findByUrl(url).get())
    }

    override fun PageURLContext<Post>.createCommand(data: ElementSource<Post>) = {
        val ctx = this
        val post = data.asSingle()
        val model = converterService.convertToView<PostViewModel>(post, this).get()
        val renderedPageElements = StringBuilder()
        renderedPageElements.append(renderPageElementListsFor(post))
        renderedPageElements.append(renderPageElementFormsFor(post))
        Templates.htmlTemplate {
            renderPostDetails(
                    model = model.copy(renderedPageElements = renderedPageElements.toString()),
                    ctx = ctx
            )
        }
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : RenderResourceDescriptor<Post> {

        override val name = "Show Post"
        override val resourceClass = Post::class
        override val type = PageRenderer(Post::class)
        override val route = PostURL.route
        override val urlClass = PostURL::class

        override fun toString() = OperationDescriptor.toString(this)
    }
}
