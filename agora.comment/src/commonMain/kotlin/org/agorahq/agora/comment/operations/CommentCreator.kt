package org.agorahq.agora.comment.operations

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.templates.COMMENT_FORM
import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.module.operations.DocumentElementCreator
import org.agorahq.agora.core.services.StorageService

class CommentCreator(
        private val commentStorage: StorageService<Comment>
) : DocumentElementCreator<Comment> {

    override val creates = Comment::class
    override val route = ROUTE

    override fun renderFormFor(parent: Page): String {
        return COMMENT_FORM.render(parent.id.toString())
    }

    override fun store(documentElement: Comment) {
        commentStorage.store(documentElement)
    }

    companion object {
        const val ROUTE = "/comments"
    }
}