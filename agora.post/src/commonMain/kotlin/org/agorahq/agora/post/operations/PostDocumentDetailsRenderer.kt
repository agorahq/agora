package org.agorahq.agora.post.operations

import org.agorahq.agora.core.domain.Site
import org.agorahq.agora.core.module.operations.DocumentDetailsRenderer
import org.agorahq.agora.core.services.DocumentQueryService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.module.PostPageURL
import org.agorahq.agora.post.templates.POST_DETAILS
import kotlin.reflect.KClass

class PostDocumentDetailsRenderer(
        private val site: Site,
        private val postQueryService: DocumentQueryService<Post>
) : DocumentDetailsRenderer<Post, PostPageURL> {

    override val route: String = PostPageURL.route

    override val permalinkType: KClass<PostPageURL> = PostPageURL::class

    override fun renderDocumentBy(permalink: PostPageURL): String {
        return POST_DETAILS.render(postQueryService.findByPermalink(permalink).get() to site)
    }

}