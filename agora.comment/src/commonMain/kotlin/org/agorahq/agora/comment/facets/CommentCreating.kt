package org.agorahq.agora.comment.facets

import org.agorahq.agora.comment.domain.Comment
import org.agorahq.agora.comment.templates.COMMENT_FORM
import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.module.facet.DocumentFeatureCreating
import org.agorahq.agora.core.service.StorageService

class CommentCreating(
        private val commentStorage: StorageService<Comment>
) : DocumentFeatureCreating<Comment> {

    override val creates = Comment::class
    override val route = ROUTE

    override fun renderFormFor(parent: Document): String {
        return COMMENT_FORM.render(parent.id.toString())
    }

    override fun store(featureObject: Comment) {
        commentStorage.store(featureObject)
    }

    companion object {
        const val ROUTE = "/comments"
    }
}