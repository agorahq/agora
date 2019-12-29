package org.agorahq.agora.post.templates

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.p
import org.agorahq.agora.core.template.template
import org.agorahq.agora.post.domain.Post

val POST_CARD = template<Post> { post ->
    div("row mb-2") {
        div("col") {
            div("card") {
                div("card-body") {
                    p { +post.shortDescription }
                    a(href = post.permalink) { +"Read more" }
                }
            }
        }
    }
}