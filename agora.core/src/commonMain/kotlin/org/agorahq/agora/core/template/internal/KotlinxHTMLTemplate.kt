package org.agorahq.markland.core.template.internal

import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML
import org.agorahq.markland.core.template.Template

class KotlinxHTMLTemplate<T>(
        private val builder: TagConsumer<*>.(T) -> Unit
) : Template<T> {

    override fun build(data: T): String {
        val sb = StringBuilder()
        val tagConsumer = sb.appendHTML(prettyPrint = false, xhtmlCompatible = true)
        builder(tagConsumer, data)
        return sb.toString()
    }
}