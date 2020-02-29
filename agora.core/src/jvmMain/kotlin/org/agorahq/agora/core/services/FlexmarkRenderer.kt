package org.agorahq.agora.core.services

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension
import com.vladsch.flexmark.ext.definition.DefinitionExtension
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.typographic.TypographicExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.parser.ParserEmulationProfile
import com.vladsch.flexmark.util.data.MutableDataSet
import org.agorahq.agora.core.api.services.MarkdownRenderer

object FlexmarkRenderer : MarkdownRenderer {

    private val options = MutableDataSet().apply {
        setFrom(ParserEmulationProfile.KRAMDOWN)
        set(Parser.EXTENSIONS, listOf(
                AbbreviationExtension.create(),
                DefinitionExtension.create(),
                FootnoteExtension.create(),
                StrikethroughExtension.create(),
                TablesExtension.create(),
                TypographicExtension.create()))
    }

    private val parser = Parser.builder(options).build()
    private val renderer = HtmlRenderer.builder(options).build()

    override fun render(markdown: String): String {
        val document = parser.parse(markdown)
        return renderer.render(document)
    }
}