package org.agorahq.agora.post.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.hr
import kotlinx.html.html
import kotlinx.html.span
import org.agorahq.agora.core.api.extensions.htmlContent
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.api.shared.templates.DEFAULT_NAVIGATION
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.post.viewmodel.PostViewModel

val POST_DETAILS = template<PostViewModel> { model ->
    html {
        include(DEFAULT_HEAD, model.title)
        body {
            include(DEFAULT_NAVIGATION, model.context)
            div("container") {
                h1 {
                    +model.title
                }
                div {
                    htmlContent(model.content)
                }
                div {
                    model.tags.forEach { tag ->
                        span("badge badge-secondary mr-1") { +tag }
                    }
                }
                hr { }
                div {
                    htmlContent(model.renderedPageElements)
                }
                include(DEFAULT_FOOTER, model.context.site)
            }
        }
    }
}