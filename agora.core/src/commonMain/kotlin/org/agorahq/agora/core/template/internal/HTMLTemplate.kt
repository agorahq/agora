package org.agorahq.agora.core.template.internal

import kotlinx.html.TagConsumer
import kotlinx.html.stream.appendHTML
import org.agorahq.agora.core.template.Template

/**
 * A [Template] which can be used to render HTML content in a textual format.
 */
class HTMLTemplate<D>(
        private val prettyPrint: Boolean = false,
        private val builder: TagConsumer<*>.(D) -> Unit
) : Template<D, String> {

    override fun render(data: D): String {
        val templateContent = StringBuilder()
        val tagConsumer = templateContent.appendHTML(prettyPrint = prettyPrint, xhtmlCompatible = true)
        builder(tagConsumer, data)
        return templateContent.toString()
    }
}