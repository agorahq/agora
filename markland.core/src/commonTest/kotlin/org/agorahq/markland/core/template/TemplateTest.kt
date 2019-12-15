package org.agorahq.markland.core.template

import kotlinx.html.*
import org.agorahq.markland.core.collection.DocumentMetadata
import org.agorahq.markland.core.collection.Site
import org.agorahq.markland.core.extensions.content
import org.agorahq.markland.core.extensions.include
import org.hexworks.cobalt.Identifier
import kotlin.test.Test
import kotlin.test.assertEquals

class TemplateTest {

    @Test
    fun should_properly_render_test_layout() {
        val actualRenderedLayout = testLayout.build(LayoutContext(
                documentMetadata = DocumentMetadata(
                        id = Identifier.randomIdentifier(),
                        title = "title",
                        tldr = "excerpt",
                        createdAt = 1,
                        markdownContent = "content",
                        permalink = "/",
                        shortDescription = "Short description"),
                site = Site(
                        title = "site title",
                        collections = listOf(),
                        email = "test@test.com",
                        description = "description",
                        host = "example.com",
                        port = 80,
                        baseUrl = "/"),
                data = Unit))

        assertEquals(expectedRenderedLayout, actualRenderedLayout)
    }

    companion object {

        private const val expectedRenderedLayout = "<html><head><title>site title</title></head><body><div class=\"container\"><h1>title</h1><div>content</div></div><footer>Copyright site title</footer></body></html>"

        private val head = template<Site> { site ->
            head {
                title(site.title)
            }
        }

        private val testLayout = layout { ctx ->
            val (document, site) = ctx
            html {
                include(head, ctx.site)
                body {
                    div("container") {
                        h1 { text(document.title) }
                        div {
                            content(document)
                        }
                    }
                    footer {
                        text("Copyright ${site.title}")
                    }
                }
            }
        }
    }


}