package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.renderResourceLinksFor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.viewmodel.PostViewModel

fun FlowContent.renderPostCard(
        context: OperationContext<out Any>,
        post: PostViewModel
) {
    val user = context.user
    val isPublished = post.isPublished
    div("row mb-3") {
        div("col") {
            div(if (isPublished) "card" else "card border-warning") {
                div("card-body") {
                    h5("card-title") {
                        +post.title
                        span("float-right" + if (isPublished) " text-secondary" else " text-warning") {
                            post.publicationDate?.let { +it }
                            if (isPublished.not()) {
                                +" (Not Published)"
                            }
                        }
                    }
                    div("card-text") {
                        p { +post.excerpt }
                        p {
                            post.tags.split(", ").map { it.trim() }.forEach { tag ->
                                span("badge badge-primary mr-2") {
                                    +tag
                                }
                            }
                        }
                        post.url?.let { url ->
                            a(href = url) { +"Read more" }
                        }
                    }
                }
                val alterers = context.renderResourceLinksFor(Post::class, post.id)
                if (alterers.isNotBlank()) {
                    div("card-footer bg-transparent") {
                        unsafe {
                            +alterers
                        }
                    }
                }
            }
        }

    }
}
