package org.agorahq.agora.post.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.html
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.extensions.content
import org.agorahq.agora.core.extensions.include
import org.agorahq.agora.core.shared.include.DEFAULT_FOOTER
import org.agorahq.agora.core.shared.include.DEFAULT_HEAD
import org.agorahq.agora.core.shared.include.DEFAULT_NAVIGATION
import org.agorahq.agora.core.template.template
import org.agorahq.agora.post.domain.Post

val POST_DETAILS = template<Pair<Post, Site>> { (post, site) ->
    html {
        include(DEFAULT_HEAD, post.title)
        body {
            include(DEFAULT_NAVIGATION, site)
            div("container") {
                h1 {
                    +post.title
                }
                content(post)
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}