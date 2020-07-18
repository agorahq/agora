package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.*
import org.agorahq.agora.core.api.data.Message

fun FlowContent.renderDefaultMessage(message: Message?) {
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
}
