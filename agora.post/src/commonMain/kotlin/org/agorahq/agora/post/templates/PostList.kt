package org.agorahq.agora.post.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.html
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.User
import org.agorahq.agora.core.extensions.include
import org.agorahq.agora.core.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.shared.templates.DEFAULT_NAVIGATION
import org.agorahq.agora.core.template.template
import org.agorahq.agora.post.domain.Post

val POST_LIST = template<Triple<Sequence<Post>, Site, User?>> { (posts, site, user) ->
    html {
        include(DEFAULT_HEAD, "${site.title} | Posts")
        body {
            include(DEFAULT_NAVIGATION, site to user)
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