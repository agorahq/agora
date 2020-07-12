package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.shared.templates.renderPencilIcon
import org.agorahq.agora.core.api.shared.templates.renderTrashIcon
import org.agorahq.agora.post.operations.DeletePost
import org.agorahq.agora.post.operations.EditPost
import org.agorahq.agora.post.viewmodel.PostViewModel

fun FlowContent.renderPostCard(post: PostViewModel) {
    val user = post.context.user
    val isPublished = post.isPublished
    div("row mb-3") {
        div("col") {
            div(if (isPublished) "card" else "card border-warning") {
                div("card-body") {
                    h5("card-title") {
                        +post.title
                        span("float-right" + if (isPublished) " text-secondary" else " text-warning") {
                            +post.publicationDate
                            if (isPublished.not()) {
                                +" (Not Published)"
                            }
                        }
                    }
                    div("card-text") {
                        p { +post.excerpt }
                        p {
                            post.tags.forEach { tag ->
                                span("badge badge-primary mr-2") {
                                    +tag
                                }
                            }
                        }
                        a(href = post.url) { +"Read more" }
                    }
                }
                with(post.context) {
                    if (user.canDoAnyOf(DeletePost, EditPost)) {
                        div("card-footer bg-transparent") {
                            if (user.canDo(EditPost)) {
                                form(EditPost.route, method = FormMethod.post) {
                                    this.style = "display: inline-block;"
                                    input(type = InputType.hidden, name = "id") {
                                        value = post.id
                                    }
                                    button(type = ButtonType.submit, classes = "btn btn-outline-warning mr-3") {
                                        renderPencilIcon("text-warning")
                                    }
                                }
                            }
                            if (user.canDo(DeletePost)) {
                                form(DeletePost.route, method = FormMethod.post) {
                                    this.style = "display: inline-block;"
                                    input(type = InputType.hidden, name = "id") {
                                        value = post.id
                                    }
                                    button(type = ButtonType.submit, classes = "btn btn-outline-danger") {
                                        renderTrashIcon("text-danger")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
