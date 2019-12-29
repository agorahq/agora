package org.agorahq.agora.post.facets

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.facet.DocumentListing
import org.agorahq.agora.core.service.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.POST_LIST

class PostDocumentListing(
        private val site: Site,
        private val postQueryService: DocumentQueryService<Post>,
        override val path: String = "/posts.html") : DocumentListing {

    override fun renderListing(): String {
        return POST_LIST.render(postQueryService.findAll() to site)
    }
}