package org.agorahq.agora.post.module

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.locations.get
import io.ktor.response.respondText
import io.ktor.routing.get
import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.KtorServerContext
import org.agorahq.agora.core.module.base.BaseModule
import org.agorahq.agora.core.module.facet.ContentRoot
import org.agorahq.agora.core.service.ContentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.POST_DETAILS
import org.agorahq.agora.post.templates.POST_LISTING

@Suppress("EXPERIMENTAL_API_USAGE")
class PostModule(
        private val site: Site,
        private val queryService: ContentQueryService<Post>
) : BaseModule<Post, KtorServerContext>(ContentRoot(CONTENT_ROOT)) {

    override val name: String = "Posts"

    override fun registerEndpoints(serverContext: KtorServerContext) {

        with(serverContext.routing) {

            get(CONTENT_ROOT) {
                call.respondText(
                        text = POST_LISTING.render(queryService.findAll() to site),
                        contentType = ContentType.Text.Html)
            }

            get<PostPermalink> { location ->
                val post = queryService.findByLocation(location).get()
                call.respondText(
                        text = POST_DETAILS.render(post to site),
                        contentType = ContentType.Text.Html)
            }
        }
    }

    companion object {

        private const val CONTENT_ROOT = "/posts"
    }
}