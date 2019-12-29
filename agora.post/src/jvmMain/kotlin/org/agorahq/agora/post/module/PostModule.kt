package org.agorahq.agora.post.module

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.Facet
import org.agorahq.agora.core.module.base.BaseModule
import org.agorahq.agora.core.service.DocumentQueryService
import org.agorahq.agora.post.domain.Post

@Suppress("EXPERIMENTAL_API_USAGE")
class PostModule(
        private val site: Site,
        private val postQueryService: DocumentQueryService<Post>,
        facets: Iterable<Facet> = listOf()
) : BaseModule<Post>(facets) {

    override val name: String = "Posts"

}