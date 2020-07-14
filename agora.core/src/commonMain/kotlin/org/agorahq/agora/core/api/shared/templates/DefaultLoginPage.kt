package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout

fun HTML.renderDefaultLoginPage(ctx: OperationContext<out Any>) = withDefaultLayout(ctx, "Log In") {
    div("card mt-2") {
        div("card-header") {
            h3 { +"Log In" }
        }
        div("card-body") {
            ul("list-group") {
                li("list-group-item") {
                    a("/login/google") { +"Google" }
                }
                li("list-group-item") {
                    a("/login/facebook") { +"Facebook" }
                }
            }
        }
    }
}
