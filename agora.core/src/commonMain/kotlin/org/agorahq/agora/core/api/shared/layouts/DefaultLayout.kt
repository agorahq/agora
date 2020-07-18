package org.agorahq.agora.core.api.shared.layouts

import kotlinx.html.*
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.shared.templates.renderDefaultFooter
import org.agorahq.agora.core.api.shared.templates.renderDefaultHead
import org.agorahq.agora.core.api.shared.templates.renderDefaultNavigation

fun HTML.withDefaultLayout(
        context: OperationContext<out Any>,
        pageTitle: String,
        fn: DIV.() -> Unit
) {
    renderDefaultHead(context.site, pageTitle)
    body {
        style {
        }
        renderDefaultNavigation(context)
        div("container") {
            fn()
            renderDefaultFooter(context.site)
        }
    }
}
