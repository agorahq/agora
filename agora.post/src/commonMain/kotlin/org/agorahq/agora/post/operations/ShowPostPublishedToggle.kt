package org.agorahq.agora.post.operations

import kotlinx.html.*
import org.agorahq.agora.core.api.data.ElementSource
import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.operation.Attributes
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.facets.ShowsResourceLink
import org.agorahq.agora.core.api.operation.types.ParameterizedRenderer
import org.agorahq.agora.core.api.operation.types.ParameterizedRendererDescriptor
import org.agorahq.agora.core.api.service.QueryService
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.ShowPostURL
import org.agorahq.agora.post.viewmodel.PostViewModel
import org.hexworks.cobalt.core.api.UUID

class ShowPostPublishedToggle(
        private val postQueryService: QueryService<Post>,
        private val converterService: ConverterService
) : ParameterizedRenderer<Post, UUID>, ParameterizedRendererDescriptor<Post, UUID> by Companion {


    override fun fetchResource(context: OperationContext<out UUID>): ElementSource<Post> {
        return ElementSource.ofMaybe(postQueryService.findById(context.input))
    }

    override fun createCommand(
            context: OperationContext<out UUID>,
            data: ElementSource<Post>
    ) = data.map { post ->
        val model = converterService.convertToView<PostViewModel>(post, context).get()
        val isPublished = model.isPublished
        Templates.htmlPartial {
            form(TogglePostPublished.route, method = FormMethod.post) {
                this.style = "display: inline-block; margin-bottom: 0;"
                input(type = InputType.hidden, name = "id") {
                    value = model.id
                }
                div("btn-group-toggle mr-2") {
                    button(
                            type = ButtonType.submit,
                            classes = "btn btn-primary ${if (isPublished) "active" else ""}"
                    ) {
                        attributes["data-toggle"] = "button"
                        attributes["aria-pressed"] = isPublished.toString()
                        if (isPublished) {
                            +"Hide"
                        } else {
                            +"Publish"
                        }
                    }
                }
            }
        }
    }.toCommand()


    override fun toString() = name

    companion object : ParameterizedRendererDescriptor<Post, UUID> {
        override val name = "Show toggle post published"
        override val attributes = Attributes.create<Post, UUID, String>(
                route = "${ShowPostURL.root}/toggle-post-published",
                urlClass = ShowPostURL::class,
                additionalAttributes = listOf(ShowsResourceLink(Post::class))
        )

    }
}
