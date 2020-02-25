package org.agorahq.agora.core.shared.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.html
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.User
import org.agorahq.agora.core.extensions.include
import org.agorahq.agora.core.template.template

val HOMEPAGE = template<Pair<Site, User?>> { (site, user) ->
    html {
        include(DEFAULT_HEAD, site.title)
        body {
            include(DEFAULT_NAVIGATION, site to user)
            div("container") {
                h1 {
                    +site.title
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}