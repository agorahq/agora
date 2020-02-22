package org.agorahq.agora.post.operations

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.operations.DocumentListing
import org.agorahq.agora.core.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.POST_LIST

class PostDocumentListing(
        private val site: Site,
        private val postQueryService: DocumentQueryService<Post>,
        override val route: String = "/posts.html") : DocumentListing {

    override fun renderListing(): String {
        return POST_LIST.render(postQueryService.findAll() to site)
    }
}