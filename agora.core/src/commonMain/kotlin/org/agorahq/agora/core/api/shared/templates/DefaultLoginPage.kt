package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.template.template

val DEFAULT_LOGIN_PAGE = template<OperationContext> { ctx ->
    val (site) = ctx
    html {
        include(DEFAULT_HEAD, site.title)
        body {
            include(DEFAULT_NAVIGATION, ctx)
            div("container") {
                div("card mt-2") {
                    div("card-header") {
                        h3 { +"Log In" }
                    }
                    div("card-body") {
                        ul("list-group") {
                            li("list-group-item") {
                                a("/login/google") { + "Google" }
                            }
                            li("list-group-item") {
                                a("/login/facebook") { + "Facebook" }
                            }
                        }
                    }
                }
                include(DEFAULT_FOOTER, site)
            }
        }
    }
}