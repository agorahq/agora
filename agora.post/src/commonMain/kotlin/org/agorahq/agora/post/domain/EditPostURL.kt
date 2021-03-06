package org.agorahq.agora.post.domain

import org.agorahq.agora.core.api.data.ResourceURL
import org.hexworks.cobalt.core.api.UUID

data class EditPostURL(
        val id: UUID,
        override val redirectTo: String? = null,
        override val pageElementToEdit: String? = null
) : ResourceURL<Post> {

    override fun generate() = "/post/$id/edit"

    override fun matches(resource: Post): Boolean {
        return resource.id == id
    }

    companion object {
        val route = "/post/{${EditPostURL::id.name}}/edit"
    }

}
