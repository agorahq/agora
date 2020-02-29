package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.document.PageURL
import org.agorahq.agora.core.api.exception.ResourceNotFoundException
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.operation.PageRenderer
import org.agorahq.agora.core.api.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostPageURL
import org.agorahq.agora.post.templates.POST_DETAILS

class PostRenderer(
        private val postQueryService: DocumentQueryService<Post>
) : PageRenderer<Post, PostPageURL> {

    override val route: String = PostPageURL.route

    override val permalinkType = PostPageURL::class

    override fun render(context: OperationContext, url: PageURL<Post>): String {
        return POST_DETAILS.render(context.toPageContext(postQueryService.findByUrl(url).orElseThrow {
            ResourceNotFoundException(Post::class, "url: ${url.generate()}")
        }))
    }

}