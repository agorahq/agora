package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.ResourceOperation
import org.agorahq.agora.core.api.services.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL

class CreatePost(
        private val postStorage: StorageService<Post>
) : ResourceOperation<Post> {

    override val resourceClass = Post::class
    override val route: String = PostURL.route

    override fun ResourceContext<Post>.execute() {
        postStorage.create(resource)
    }

}