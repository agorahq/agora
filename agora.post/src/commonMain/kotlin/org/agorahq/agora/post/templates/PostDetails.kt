package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.*
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.api.shared.templates.DEFAULT_NAVIGATION
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.post.domain.Post

val POST_DETAILS = template<ResourceContext<Post>> { ctx ->
    val (site, _, post) = ctx
    html {
        include(DEFAULT_HEAD, post.title)
        body {
            include(DEFAULT_NAVIGATION, ctx)
            div("container") {
                h1 {
                    +post.title
                }
                div {
                    documentContent(post)
                }
                div {
                    post.tags.forEach { tag ->
                        span("badge badge-secondary mr-1") { +tag }
                    }
                }
                hr { }
                div {
                    site.forEachModuleWithOperation<AnyPageContentListRenderer> { (_, operation) ->
                        htmlContent(with(operation) { ctx.execute() })
                    }
                    site.forEachModuleWithOperation<AnyContentFormRenderer> { (_, operation) ->
                        htmlContent(with(operation) {
                            ctx.execute()
                        })
                    }
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}