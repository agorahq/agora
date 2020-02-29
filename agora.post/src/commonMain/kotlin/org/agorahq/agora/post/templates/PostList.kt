package org.agorahq.agora.post.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.html
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.module.context.ContentListingContext
import org.agorahq.agora.core.api.module.context.PageContext
import org.agorahq.agora.core.api.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.api.shared.templates.DEFAULT_NAVIGATION
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.post.domain.Post

val POST_LIST = template<ContentListingContext<Post>> { ctx ->
    val (site, _, posts) = ctx
    html {
        include(DEFAULT_HEAD, "${site.title} | Posts")
        body {
            include(DEFAULT_NAVIGATION, ctx)
            div("container") {
                h1 {
                    +"Posts"
                }
                posts.forEach { post ->
                    include(POST_CARD, post)
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}