package org.agorahq.agora.core.api.template

import kotlinx.html.*
import org.agorahq.agora.core.api.extensions.documentContent
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.shared.templates.Templates
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
import org.agorahq.agora.core.internal.service.DefaultModuleRegistry
import kotlin.test.Test
import kotlin.test.assertEquals

class TemplateTest {

    @Test
    fun should_properly_render_simple_layout() {
        val actualRenderedLayout = Templates.htmlTemplate(prettyPrint = false) {
            renderPostTemplate(PostData(POST, SITE))
        }

        assertEquals(EXPECTED_RENDERED_POST_TEMPLATE, actualRenderedLayout)
    }

    companion object {

        private const val EXPECTED_RENDERED_POST_TEMPLATE = "<html><head><title>Christmas is coming</title></head><body><div class=\"container\"><h1>Christmas is coming</h1><div><p>Better get prepared.</p>\n" +
                "</div></div><footer>Copyright Test site. Contact: test@test.com</footer></body></html>"

        private const val POST_COLLECTION_NAME = "Posts"

        private val TEST_USER = User.create(
                email = "test@test.com",
                username = "testuser")

        private val POST = TestPost(
                title = "Christmas is coming",
                date = "2019-12-21",
                tags = setOf("post", "christmas"),
                excerpt = "Christmas is here soon.",
                shortDescription = "Christmas is here soon, so we better buy presents.",
                content = "Better get prepared.",
                owner = TEST_USER)

        private fun HTML.renderHead(title: String) {
            head {
                title(title)
            }
        }

        private fun HTML.renderPostTemplate(postData: PostData) {
            val (post, site) = postData
            renderHead(post.title)
            body {
                div("container") {
                    h1 {
                        +post.title
                    }
                    div {
                        documentContent(post)
                    }
                }
                footer {
                    text("Copyright ${site.title}. Contact: ${site.email}")
                }
            }
        }

        private val SITE = DefaultSiteMetadata(
                title = "Test site",
                email = "test@test.com",
                description = "description",
                baseUrl = "/",
                moduleRegistry = DefaultModuleRegistry())

    }

}
