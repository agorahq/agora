package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.html
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.template.template

val DEFAULT_HOMEPAGE = template<OperationContext> { ctx ->
    val (site) = ctx
    html {
        include(DEFAULT_HEAD, site.title)
        body {
            include(DEFAULT_NAVIGATION, ctx)
            div("container") {
                h1 {
                    +site.title
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}