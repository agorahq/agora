package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout
import org.agorahq.agora.post.viewmodel.PostViewModel

fun HTML.renderPostEditor(
        path: String,
        post: PostViewModel,
        context: OperationContext<out Any>
) = withDefaultLayout(
        context = context,
        pageTitle = "Editing ${post.title} | Agora"
) {
    h1("mt-3") {
        +"Editing '${post.title}'"
    }
    form(path, method = FormMethod.post, classes = "mt-3") {
        input(
                type = InputType.hidden,
                name = PostViewModel::id.name
        ) {
            value = post.id
        }
        input(
                type = InputType.hidden,
                name = PostViewModel::ownerId.name
        ) {
            value = post.ownerId
        }
        div("form-group") {
            label {
                htmlFor = PostViewModel::title.name
                +"Title"
            }
            input(
                    classes = "form-control form-control-lg",
                    type = InputType.text,
                    name = PostViewModel::title.name
            ) {
                id = PostViewModel::title.name
                value = post.title
            }
        }
        div("form-group") {
            label {
                htmlFor = PostViewModel::excerpt.name
                +"Excerpt"
            }
            input(
                    classes = "form-control",
                    type = InputType.text,
                    name = PostViewModel::excerpt.name
            ) {
                id = PostViewModel::excerpt.name
                value = post.excerpt
            }
        }
        div("form-group") {
            label {
                htmlFor = PostViewModel::abstract.name
                +"Abstract"
            }
            input(
                    classes = "form-control",
                    type = InputType.text,
                    name = PostViewModel::abstract.name
            ) {
                id = PostViewModel::abstract.name
                value = post.abstract
            }
        }
        div("form-group") {
            label {
                htmlFor = PostViewModel::content.name
                +"Content"
            }
            textArea(rows = "20", cols = "80", classes = "form-control") {
                id = PostViewModel::content.name
                name = PostViewModel::content.name
                +post.content
            }
        }
        div("form-group") {
            label {
                htmlFor = PostViewModel::tags.name
                +"Tags"
            }
            input(
                    classes = "form-control",
                    type = InputType.text,
                    name = PostViewModel::tags.name
            ) {
                id = PostViewModel::tags.name
                value = post.tags
            }
        }
        input(classes = "btn btn-primary", type = InputType.submit) {
            value = "Go"
        }
    }
}
