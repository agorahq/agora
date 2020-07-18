package org.agorahq.agora.comment.templates

import kotlinx.html.*
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel

fun FlowContent.renderCommentForm(model: CommentViewModel) {
    form(action = CommentURL.root, method = FormMethod.post) {
        div("form-group") {
            p {
                +"Commenting as "
                strong { +model.username }
            }
        }
        div("form-group") {
            input(
                    type = InputType.hidden,
                    name = CommentViewModel::id.name) {
                value = model.id
            }
            input(
                    classes = "form-control",
                    type = InputType.text,
                    name = CommentViewModel::content.name) {
                value = model.content
            }
        }
        input(type = InputType.hidden, name = CommentViewModel::parentId.name) {
            value = model.parentId
        }
        input(classes = "btn btn-primary", type = InputType.submit) {
            value = "Go"
        }
    }
}
