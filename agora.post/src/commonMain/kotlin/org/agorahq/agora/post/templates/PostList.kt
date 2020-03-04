package org.agorahq.agora.post.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.html
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.api.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.api.shared.templates.DEFAULT_NAVIGATION
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.post.viewmodel.PostListViewModel

val POST_LIST = template<PostListViewModel> { model ->
    val (posts, ctx) = model
    val (site, user) = ctx
    html {
        include(DEFAULT_HEAD, "${site.title} | Posts")
        body {
            include(DEFAULT_NAVIGATION, model.context)
            div("container") {
                h1 {
                    +"Posts"
                }
                posts.forEach { model ->
                    include(POST_CARD, model)
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}