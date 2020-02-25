package org.agorahq.agora.post.operations

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.operations.PageLister
import org.agorahq.agora.core.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.POST_LIST

class PostLister(
        private val site: Site,
        private val postQueryService: DocumentQueryService<Post>,
        override val route: String = "/posts.html"
) : PageLister {

    override fun renderListing() = POST_LIST.render(Triple(postQueryService.findAll(), site, null))
}