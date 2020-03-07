package org.agorahq.agora.core.api.template

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.title
import org.agorahq.agora.core.api.data.UserMetadata
import org.agorahq.agora.core.api.extensions.documentContent
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
import org.agorahq.agora.core.internal.service.DefaultModuleRegistry
import kotlin.test.Test
import kotlin.test.assertEquals

class TemplateTest {

    @Test
    fun should_properly_render_simple_layout() {
        val actualRenderedLayout = POST_TEMPLATE.render(PostData(POST, SITE))

        assertEquals(EXPECTED_RENDERED_POST_TEMPLATE, actualRenderedLayout)
    }

    companion object {

        private const val EXPECTED_RENDERED_POST_TEMPLATE = "<html><head><title>Christmas is coming</title></head><body><div class=\"container\"><h1>Christmas is coming</h1><div><p>Better get prepared.</p>\n" +
                "</div></div><footer>Copyright Test site. Contact: test@test.com</footer></body></html>"

        private const val POST_COLLECTION_NAME = "Posts"

        private val TEST_USER = User.create(
                email = "test@test.com",
                username = "testuser").toUser()

        private val POST = TestPost(
                title = "Christmas is coming",
                date = "2019-12-21",
                tags = setOf("post", "christmas"),
                excerpt = "Christmas is here soon.",
                shortDescription = "Christmas is here soon, so we better buy presents.",
                content = "Better get prepared.",
                owner = TEST_USER)

        private val HEAD = template<String> { title ->
            head {
                title(title)
            }
        }

        private val POST_TEMPLATE = template<PostData> { (post, site) ->
            html {
                include(HEAD, post.title)
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
        }

        private val SITE = DefaultSiteMetadata(
                title = "Test site",
                email = "test@test.com",
                description = "description",
                baseUrl = "/",
                moduleRegistry = DefaultModuleRegistry())

    }

}