package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.renderer.ResourceListRenderer
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.POST_LIST

class ListPosts(
        private val postQueryService: PageQueryService<Post>
) : ResourceListRenderer<Post, OperationContext> {

    override val route: String = "/posts.html"
    override val resourceClass = Post::class

    override fun OperationContext.reify() = {
        POST_LIST.render(postQueryService.findAll().toViewListingContext())
    }.toCommand()

}