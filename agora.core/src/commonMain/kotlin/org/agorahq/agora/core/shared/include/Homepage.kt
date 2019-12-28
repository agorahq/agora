package org.agorahq.agora.core.shared.include

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.html
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.extensions.include
import org.agorahq.agora.core.template.template

val HOMEPAGE = template<Site> { site ->
    html {
        include(DEFAULT_HEAD, site.title)
        body {
            include(DEFAULT_NAVIGATION, site)
            div("container") {
                h1 {
                    +site.title
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}