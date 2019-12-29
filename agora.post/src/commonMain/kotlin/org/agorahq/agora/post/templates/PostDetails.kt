package org.agorahq.agora.post.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.hr
import kotlinx.html.html
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.extensions.documentContent
import org.agorahq.agora.core.extensions.forEachModuleWithFacet
import org.agorahq.agora.core.extensions.htmlContent
import org.agorahq.agora.core.extensions.include
import org.agorahq.agora.core.module.facet.DocumentFeatureListing
import org.agorahq.agora.core.shared.templates.DEFAULT_FOOTER
import org.agorahq.agora.core.shared.templates.DEFAULT_HEAD
import org.agorahq.agora.core.shared.templates.DEFAULT_NAVIGATION
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
                div {
                    documentContent(post)
                }
                hr { }
                div {
                    site.forEachModuleWithFacet<DocumentFeatureListing> { (_, facet) ->
                        htmlContent(facet.renderListingFor(post))
                    }
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}