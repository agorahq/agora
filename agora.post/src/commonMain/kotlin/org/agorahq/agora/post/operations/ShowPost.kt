package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.extensions.renderPageElementFormsFor
import org.agorahq.agora.core.api.extensions.renderPageElementListsFor
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.PageRenderer
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
) : ParameterizedRenderer<Post, ResourceURL<Post>>, ParameterizedRendererDescriptor<Post, ResourceURL<Post>> by Companion {

    override fun fetchResource(context: OperationContext<out ResourceURL<Post>>): ElementSource<Post> {
        return ElementSource.fromElement(postQueryService.findByUrl(context.input).get())
    }

    override fun createCommand(context: OperationContext<out ResourceURL<Post>>, data: ElementSource<Post>) = {
        val post = data.asSingle()
        val model = converterService.convertToView<PostViewModel>(post, context).get()
        val renderedPageElements = StringBuilder()
        renderedPageElements.append(context.renderPageElementListsFor(post))
        renderedPageElements.append(context.renderPageElementFormsFor(post))
        Templates.htmlTemplate {
            renderPostDetails(
                    post = model.copy(renderedPageElements = renderedPageElements.toString()),
                    ctx = context
            )
        }
    }.toCommand()

    override fun toString() = OperationDescriptor.toString(this)

    companion object : ParameterizedRendererDescriptor<Post, ResourceURL<Post>> by OperationDescriptor.create(
            name = "Show Post",
            route = PostURL.route,
            urlClass = PostURL::class,
            facets = listOf(PageRenderer)
    )
}
