package org.agorahq.agora.core.api.shared.layouts

import kotlinx.html.*
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.templates.renderDefaultFooter
import org.agorahq.agora.core.api.shared.templates.renderDefaultHead
import org.agorahq.agora.core.api.shared.templates.renderDefaultNavigation

fun HTML.withDefaultLayout(
        ctx: OperationContext,
        pageTitle: String,
        fn: DIV.() -> Unit
) {
    renderDefaultHead(ctx.site, pageTitle)
    body {
        renderDefaultNavigation(ctx)
        div("container") {
            fn()
            renderDefaultFooter(ctx.site)
        }
        script {
            src = "static/jquery-3.2.1.slim.min.js"
        }
        script {
            src = "static/popper.min.js"
        }
        script {
            src = "static/bootstrap.min.js"
        }
    }
}
