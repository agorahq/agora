package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.template.template

val DEFAULT_HOMEPAGE = template<OperationContext> { ctx ->
    val (site, _, _, message) = ctx
    html {
        include(DEFAULT_HEAD, site.title)
        message?.let {
            div("alert alert-${it.type.name.toLowerCase()} alert-dismissable fade show") {
                attributes["role"] = "alert"
                + it.text
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