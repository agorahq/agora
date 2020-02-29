package org.agorahq.agora.core.template

import kotlinx.html.*
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.extensions.documentContent
import org.agorahq.agora.core.api.extensions.include
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.services.ModuleRegistry
import org.agorahq.agora.core.api.template.template
import org.agorahq.agora.core.internal.data.DefaultSiteMetadata
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
                content = "Better get prepared.")

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
                moduleRegistry = object : ModuleRegistry {
                    override val modules: Iterable<Module<out Content>>
                        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

                    override fun <D : Content> findModule(klass: KClass<Module<D>>): Maybe<Module<D>> {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun register(module: Module<out Content>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

    }

}