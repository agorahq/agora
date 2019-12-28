package org.agorahq.agora.core.shared.include

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.li
import kotlinx.html.nav
import kotlinx.html.ul
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.extensions.filterForFacet
import org.agorahq.agora.core.extensions.modules
import org.agorahq.agora.core.module.facet.ContentRoot
import org.agorahq.agora.core.template.template

val DEFAULT_NAVIGATION = template<Site> { site ->
    nav("navbar navbar-expand-lg navbar-dark bg-dark") {
        div("container") {
            div("collapse navbar-collapse") {
                ul("navbar-nav mr-auto") {
                    li("nav-item") {
                        a(href = "/", classes = "nav-link") { +"Home" }
                    }
                    site.modules.filterForFacet<ContentRoot>().forEach { (module, facet) ->
                        li("nav-item") {
                            a(href = facet.value, classes = "nav-link") { +module.name }
                        }
                    }
                }
            }
        }
    }
}