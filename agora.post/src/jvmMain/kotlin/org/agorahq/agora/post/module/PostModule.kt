package org.agorahq.agora.post.module

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.Operation
import org.agorahq.agora.core.module.base.BaseModule
import org.agorahq.agora.core.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post

@Suppress("EXPERIMENTAL_API_USAGE")
class PostModule(
        private val site: Site,
        private val postQueryService: DocumentQueryService<Post>,
        operations: Iterable<Operation> = listOf()
) : BaseModule<Post>(operations) {

    override val name: String = "Posts"

}