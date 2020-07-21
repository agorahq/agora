package org.agorahq.agora.comment.domain

import org.agorahq.agora.core.api.data.ResourceURLWithId
import org.hexworks.cobalt.core.api.UUID

data class DeleteCommentURL(
        override val id: UUID,
        override val redirectTo: String? = null,
        override val pageElementToEdit: String? = null
) : ResourceURLWithId<Comment> {

    override fun generate() = "/$root/$id/delete"

    override fun matches(resource: Comment): Boolean {
        return resource.id == id
    }

    companion object {
        const val root = "comments"
        val route = "/$root/{${DeleteCommentURL::id.name}}/delete"
    }

}
