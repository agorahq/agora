package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.li
import kotlinx.html.nav
import kotlinx.html.span
import kotlinx.html.ul
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.extensions.forEachModuleWithOperation
import org.agorahq.agora.core.api.extensions.isAnonymous
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.operation.PageListRenderer
import org.agorahq.agora.core.api.template.template

val DEFAULT_NAVIGATION = template<OperationContext> { ctx ->
    val (site, user) = ctx
    nav("navbar navbar-expand-lg navbar-dark bg-dark") {
        div("container") {
            div("collapse navbar-collapse") {
                ul("navbar-nav mr-auto") {
                    li("nav-item") {
                        a(href = "/", classes = "nav-link") { +"Home" }
                    }
                    site.forEachModuleWithOperation<PageListRenderer<out Page>> { (module, listing) ->
                        li("nav-item") {
                            val classes = if (module.supportsContext(ctx)) {
                                "nav-link active"
                            } else "nav-link"
                            a(href = listing.route, classes = classes) { +module.name }
                        }
                    }
                }
            }
            div("nav navbar-nav navbar-right") {
                if (user.isAnonymous) {
                    a("/login", classes = "nav-link") { +"Log in" }
                } else {
                    span("navbar-text") { +"Hello, ${user.username}." }
                    a("/logout", classes = "nav-link") { +"Logout" }
                }
            }

        }
    }
}