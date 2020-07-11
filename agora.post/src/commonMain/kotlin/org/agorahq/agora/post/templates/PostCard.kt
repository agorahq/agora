package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.post.viewmodel.PostViewModel

fun FlowContent.renderPostCard(model: PostViewModel) {
    div("row mb-2") {
        div("col") {
            div("card") {
                div("card-body") {
                    h5("card-title") {
                        +model.title
                        span("float-right") { +model.publicationDate }
                    }
                    div("card-text") {
                        p { +model.excerpt }
                        p {
                            model.tags.forEach { tag ->
                                span("badge badge-primary mr-2") {
                                    +tag
                                }
                            }
                        }
                        a(href = model.url) { +"Read more" }
                    }
                }
            }
        }
    }
}
