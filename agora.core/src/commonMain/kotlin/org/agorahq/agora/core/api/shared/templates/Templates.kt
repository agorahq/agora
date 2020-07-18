package org.agorahq.agora.core.api.shared.templates

import kotlinx.html.HTML
import kotlinx.html.TagConsumer
import kotlinx.html.html
import kotlinx.html.stream.appendHTML

object Templates {

    fun htmlTemplate(
            prettyPrint: Boolean = false,
            fn: HTML.() -> Unit
    ): String {
        val sb = StringBuilder()
        sb.appendHTML(
                prettyPrint = prettyPrint,
                xhtmlCompatible = true
        ).html {
            fn()
        }
        return sb.toString()
    }

    fun htmlPartial(
            prettyPrint: Boolean = false,
            fn: TagConsumer<StringBuilder>.() -> Unit
    ): String {
        val sb = StringBuilder()
        with(sb.appendHTML(
                prettyPrint = prettyPrint,
                xhtmlCompatible = true
        )) {
            fn()
        }
        return sb.toString()
    }

}
