package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.templates.COMMENT_FORM
import org.agorahq.agora.core.api.CorePermissions
import org.agorahq.agora.core.api.CorePermissions.*
import org.agorahq.agora.core.api.behavior.Secured
import org.agorahq.agora.core.api.behavior.Secured.Companion.permissions
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.operation.PageContentCreator
import org.agorahq.agora.core.api.services.StorageService

class CreateComment(
        private val commentStorage: StorageService<Comment>
) : PageContentCreator<Comment>, Secured by permissions(CREATE_COMMENT) {

    override val creates = Comment::class
    override val route = ROUTE

    override fun renderFormFor(parent: Page): String {
        return COMMENT_FORM.render(parent.id.toString())
    }

    override fun save(documentElement: Comment) {
        commentStorage.store(documentElement)
    }

    companion object {
        const val ROUTE = "/comments"
    }
}