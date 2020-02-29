package org.agorahq.agora.comment.templates

import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.form
import kotlinx.html.input
import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.template.template

val COMMENT_FORM = template<ResourceContext<out Page>> { ctx ->
    val (_, user, page) = ctx
    form(action = CommentURL.root, method = FormMethod.post) {
        input(type = InputType.text, name = Comment::content.name)
        input(type = InputType.hidden, name = Comment::parentId.name) {
            value = page.id.toString()
        }
        input(type = InputType.hidden, name = Comment::author.name) {
            value = user.username
        }
        input(type = InputType.submit) {
            value = "Go"
        }
    }
}