package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.operation.PageListRenderer
import org.agorahq.agora.core.api.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.POST_LIST

class ListPosts(
        private val postQueryService: DocumentQueryService<Post>,
        override val route: String = "/posts.html"
) : PageListRenderer<Post> {

    override fun render(context: OperationContext): String {
        return POST_LIST.render(context.toPageListingContext(postQueryService.findAll()))
    }
}