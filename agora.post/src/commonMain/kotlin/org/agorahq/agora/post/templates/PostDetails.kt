package org.agorahq.agora.post.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.extensions.documentContent
import org.agorahq.agora.core.api.extensions.forEachModuleWithOperation
import org.agorahq.agora.core.api.extensions.htmlContent
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.module.context.PageContext
import org.agorahq.agora.core.api.module.operation.PageContentListRenderer
import org.agorahq.agora.core.api.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.api.shared.templates.DEFAULT_NAVIGATION
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.post.domain.Post

val POST_DETAILS = template<PageContext<Post>> { ctx ->
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
                hr { }
                div {
                    site.forEachModuleWithOperation<PageContentListRenderer<Content>> { (_, operation) ->
                        htmlContent(operation.render(ctx, post))
                    }
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}