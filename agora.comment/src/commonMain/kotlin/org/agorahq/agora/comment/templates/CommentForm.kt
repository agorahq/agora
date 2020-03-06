package org.agorahq.agora.comment.templates

import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.form
import kotlinx.html.input
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.comment.viewmodel.CommentViewModel
import org.agorahq.agora.core.api.template.template

val COMMENT_FORM = template<CommentViewModel> { model ->
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