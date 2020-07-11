package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.FlowContent
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.html
import kotlinx.html.stream.appendHTML

object Templates {

    fun htmlTemplate(fn: HTML.() -> Unit): String {
        val sb = StringBuilder()
        sb.appendHTML(
                prettyPrint = true,
                xhtmlCompatible = true
        ).html {
            fn()
        }
        return sb.toString()
    }

    fun htmlPartial(fn: FlowContent.() -> Unit): String {
        val sb = StringBuilder()
        sb.appendHTML(
                prettyPrint = true,
                xhtmlCompatible = true
        ).div {
            fn()
        }
        return sb.toString()
    }

}
