package org.agorahq.agora.core.shared.templates

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.li
import kotlinx.html.nav
import kotlinx.html.span
import kotlinx.html.ul
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.User
import org.agorahq.agora.core.extensions.forEachModuleWithOperation
import org.agorahq.agora.core.module.operations.PageLister
import org.agorahq.agora.core.template.template

val DEFAULT_NAVIGATION = template<Pair<Site, User?>> { (site, user) ->
    nav("navbar navbar-expand-lg navbar-dark bg-dark") {
        div("container") {
            div("collapse navbar-collapse") {
                ul("navbar-nav mr-auto") {
                    li("nav-item") {
                        a(href = "/", classes = "nav-link") { +"Home" }
                    }
                    site.forEachModuleWithOperation<PageLister> { (module, listing) ->
                        li("nav-item") {
                            a(href = listing.route, classes = "nav-link") { +module.name }
                        }
                    }
                }
            }
            div("nav navbar-nav navbar-right") {
                user?.let {
                    span("navbar-text") { +"Hello, ${user.firstName}." }
                    a("/logout", classes = "nav-link") { +"Logout" }
                } ?: a("/login", classes = "nav-link") { +"Log in" }
            }

        }
    }
}