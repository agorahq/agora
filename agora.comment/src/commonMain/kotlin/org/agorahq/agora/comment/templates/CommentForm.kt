package org.agorahq.agora.comment.templates

import kotlinx.html.*
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel

fun FlowContent.renderCommentForm(model: CommentViewModel) {
    form(action = CommentURL.root, method = FormMethod.post) {
        input(type = InputType.text, name = CommentViewModel::content.name)
        input(type = InputType.hidden, name = CommentViewModel::parentId.name) {
            value = model.parentId
        }
        input(type = InputType.submit) {
            value = "Go"
        }
    }
}
