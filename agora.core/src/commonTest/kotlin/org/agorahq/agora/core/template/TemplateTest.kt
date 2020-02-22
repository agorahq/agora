package org.agorahq.agora.core.template

import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.footer
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.title
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.domain.document.DocumentPart
import org.agorahq.agora.core.extensions.documentContent
import org.agorahq.agora.core.extensions.include
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.services.ModuleRegistry
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.reflect.KClass
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
        private val POST = Post(
                title = "Christmas is coming",
                date = "2019-12-21",
                excerpt = "Christmas is here soon.",
                shortDescription = "Christmas is here soon, so we better buy presents.",
                rawContent = "Better get prepared.")

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

        private val SITE = Site(
                title = "Test site",
                email = "test@test.com",
                description = "description",
                host = "example.com",
                port = 80,
                baseUrl = "/",
                moduleRegistry = object : ModuleRegistry {
                    override val modules: Iterable<Module<out DocumentPart>>
                        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

                    override fun <D : DocumentPart> findModule(klass: KClass<Module<D>>): Maybe<Module<D>> {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun register(module: Module<out DocumentPart>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

    }

}