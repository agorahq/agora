package org.agorahq.agora.comment.templates

import kotlinx.html.*
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel

fun TagConsumer<Appendable>.renderCommentForm(comment: CommentViewModel) {
    form(action = CommentURL.root, method = FormMethod.post) {
        div("form-group") {
            p {
                +"Commenting as "
                strong { +comment.username }
            }
        }
        div("form-group") {
            input(
                    type = InputType.hidden,
                    name = CommentViewModel::id.name) {
                value = comment.id
            }
            input(
                    classes = "form-control",
                    type = InputType.text,
                    name = CommentViewModel::content.name) {
                value = comment.content
            }
        }
        input(type = InputType.hidden, name = CommentViewModel::parentId.name) {
            value = comment.parentId
        }
        input(classes = "btn btn-primary", type = InputType.submit) {
            value = "Go"
        }
    }
}
