package org.agorahq.agora.core.api.fixture

import org.agorahq.agora.core.api.data.ResourceURL

data class CommentURL(
        val id: String,
        override val redirectTo: String? = null,
        override val pageElementToEdit: String? = null
) : ResourceURL<Comment> {

    override fun generate() = "/$root/$id"

    override fun matches(resource: Comment): Boolean {
        return resource.id.toString() == id
    }

    companion object {
        const val root = "/comments"
    }

}
