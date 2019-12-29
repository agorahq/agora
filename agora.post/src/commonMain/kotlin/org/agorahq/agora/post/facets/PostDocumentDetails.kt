package org.agorahq.agora.post.facets

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.facet.DocumentDetails
import org.agorahq.agora.core.service.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostPermalink
import org.agorahq.agora.post.templates.POST_DETAILS
import kotlin.reflect.KClass

class PostDocumentDetails(
        private val site: Site,
        private val postQueryService: DocumentQueryService<Post>
) : DocumentDetails<Post, PostPermalink> {

    override val route: String = PostPermalink.route

    override val permalinkType: KClass<PostPermalink> = PostPermalink::class

    override fun renderDocumentBy(permalink: PostPermalink): String {
        return POST_DETAILS.render(postQueryService.findByPermalink(permalink).get() to site)
    }

}