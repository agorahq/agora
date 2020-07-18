package org.agorahq.agora.comment.domain

import org.agorahq.agora.core.api.data.ResourceURL

data class EditCommentURL(
        val id: String,
        override val redirectTo: String? = null,
        override val pageElementsToEdit: Iterable<String> = listOf()
) : ResourceURL<Comment> {

    override fun generate() = route.replace("{${EditCommentURL::id.name}}", id)

    override fun matches(resource: Comment): Boolean {
        return resource.id.toString() == id
    }

    companion object {
        val route = "/comment/{${EditCommentURL::id.name}}/edit"
    }

}
