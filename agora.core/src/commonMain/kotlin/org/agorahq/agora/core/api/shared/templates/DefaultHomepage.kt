package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.layouts.withDefaultLayout

fun HTML.renderDefaultHomepage(ctx: OperationContext) = withDefaultLayout(ctx, "Home") {
    val (site, _, _, message) = ctx
    message?.let {
        div("alert alert-${it.type.name.toLowerCase()} alert-dismissable fade show mt-3") {
            attributes["role"] = "alert"
            +it.text
            button(type = ButtonType.button, classes = "close") {
                attributes["data-dismiss"] = "alert"
                attributes["aria-label"] = "Close"
                span {
                    attributes["aria-hidden"] = "true"
                    unsafe {
                        +"&times"
                    }
                }
            }
        }
    }
    h1 {
        +site.title
    }
}
