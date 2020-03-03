package org.agorahq.agora.post.templates

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.p
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.post.viewmodel.PostViewModel

val POST_CARD = template<PostViewModel> { model ->
    div("row mb-2") {
        div("col") {
            div("card") {
                div("card-body") {
                    p { +model.shortDescription }
                    a(href = model.url) { +"Read more" }
                }
            }
        }
    }
}