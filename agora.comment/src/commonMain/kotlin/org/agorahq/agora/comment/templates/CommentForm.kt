package org.agorahq.agora.comment.templates

import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.form
import kotlinx.html.input
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.operations.CreateComment
import org.agorahq.agora.core.api.template.template

val COMMENT_FORM = template<String> { parentId ->
    form(action = CreateComment.ROUTE, method = FormMethod.post) {
        input(type = InputType.text, name = Comment::content.name)
        input(type = InputType.hidden, name = Comment::parentId.name) {
            value = parentId
        }
        input(type = InputType.hidden, name = Comment::author.name) {
            value = "Anonymous"
        }
        input(type = InputType.submit) {
            value = "Go"
        }
    }
}