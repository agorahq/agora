package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.extensions.toCommand
import org.agorahq.agora.core.api.module.context.PageRequestContext
import org.agorahq.agora.core.api.module.renderer.PageRenderer
import org.agorahq.agora.core.api.service.PageQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.templates.POST_DETAILS

class ShowPost(
        private val postQueryService: PageQueryService<Post>
) : PageRenderer<Post, PostURL> {

    override val urlType = PostURL::class
    override val route: String = PostURL.route
    override val resourceClass = Post::class

    override fun PageRequestContext<Post>.reify() = {
        POST_DETAILS.render(postQueryService.findByUrl(url).toViewContext())
    }.toCommand()

}