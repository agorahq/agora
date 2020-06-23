package org.agorahq.agora.comment.domain

import org.agorahq.agora.core.api.data.ResourceURL

data class CommentURL(
        val id: String
) : ResourceURL<Comment> {

    override fun generate() = "/$root/$id"

    override fun matches(resource: Comment): Boolean {
        return resource.id.toString() == id
    }

    companion object {
        const val root = "/comments"
        val route = "$root/{${CommentURL::id.name}}"
    }

}
