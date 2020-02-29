package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.renderer.ContentResourceListRenderer
import org.agorahq.agora.core.api.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.templates.POST_LIST

class ListPosts(
        private val postQueryService: DocumentQueryService<Post>
) : ContentResourceListRenderer<Post, OperationContext> {

    override val route: String = "/posts.html"
    override val resourceClass = Post::class

    override fun OperationContext.execute() = POST_LIST.render(toListingContext(postQueryService.findAll()))

}