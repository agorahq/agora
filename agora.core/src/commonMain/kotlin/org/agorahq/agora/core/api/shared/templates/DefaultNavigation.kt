package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.li
import kotlinx.html.nav
import kotlinx.html.span
import kotlinx.html.ul
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.forEachModuleHavingOperationWithType
import org.agorahq.agora.core.api.extensions.isAnonymous
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.OperationType.PageListRenderer
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
                    site.forEachModuleHavingOperationWithType(PageListRenderer(Page::class)) { (module, renderer) ->
                        li("nav-item") {
                            val classes = if (module.supportsContext(ctx)) {
                                "nav-link active"
                            } else "nav-link"
                            a(href = renderer.route, classes = classes) { +module.name }
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
