package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.exception.ResourceNotFoundException
import org.agorahq.agora.core.api.module.context.PageContext
import org.agorahq.agora.core.api.module.renderer.PageRenderer
import org.agorahq.agora.core.api.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL
import org.agorahq.agora.post.templates.POST_DETAILS

class RenderPost(
        private val postQueryService: DocumentQueryService<Post>
) : PageRenderer<Post, PostURL> {

    override val urlType = PostURL::class
    override val route: String = PostURL.route
    override val resourceClass = Post::class

    override fun PageContext<Post>.execute(): String {
        val post = postQueryService.findByUrl(url).orElseThrow {
            ResourceNotFoundException(Post::class, "url: ${url.generate()}")
        }
        return POST_DETAILS.render(toResourceContext(post))
    }

}