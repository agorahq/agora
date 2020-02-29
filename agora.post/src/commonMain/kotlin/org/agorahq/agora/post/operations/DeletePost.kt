package org.agorahq.agora.post.operations

import org.agorahq.agora.core.api.DefaultPermissions.DELETE_POST
import org.agorahq.agora.core.api.behavior.Secured
import org.agorahq.agora.core.api.behavior.Secured.Companion.permissions
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.ResourceOperation
import org.agorahq.agora.core.api.services.StorageService
import org.agorahq.agora.post.domain.Post
import org.agorahq.agora.post.domain.PostURL

class DeletePost(
        private val postStorage: StorageService<Post>
) : ResourceOperation<Post>, Secured by permissions(DELETE_POST) {

    override val resourceClass = Post::class
    override val route: String = PostURL.route

    private val deletePost = this

    override fun ResourceContext<Post>.execute() = postStorage.delete(resource)

}