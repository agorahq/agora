package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.domain.CommentURL
import org.agorahq.agora.core.api.DefaultPermissions.CREATE_COMMENT
import org.agorahq.agora.core.api.behavior.Secured
import org.agorahq.agora.core.api.behavior.Secured.Companion.permissions
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.ResourceOperation
import org.agorahq.agora.core.api.services.StorageService

class CreateComment(
        private val commentStorage: StorageService<Comment>
) : ResourceOperation<Comment>, Secured by permissions(CREATE_COMMENT) {

    override val resourceClass = Comment::class
    override val route = CommentURL.root

    override fun ResourceContext<Comment>.execute() {
        commentStorage.create(resource)
    }

    override fun toString() = "Operation: Create Comment"
}