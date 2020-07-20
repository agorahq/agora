package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.isAnonymous
import org.agorahq.agora.core.api.operation.context.OperationContext

fun FlowContent.renderDefaultNavigation(ctx: OperationContext<out Any>) {
    val (site, user) = ctx
    nav("navbar navbar-expand-lg navbar-dark bg-dark") {
        div("container") {
            div("collapse navbar-collapse") {
                ul("navbar-nav mr-auto") {
                    li("nav-item") {
                        a(href = "/", classes = "nav-link") { +"Home" }
                    }
                    // TODO: fix this
//                    site.forEachMatchingOperation<Resource, Any, Any>(ListsResources) { (module, renderers) ->
//                        li("nav-item") {
//                            // TODO: check for active
//                            val classes = "nav-link"
//                            a(href = renderer.route, classes = classes) { +module.name }
//                        }
//                    }
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
