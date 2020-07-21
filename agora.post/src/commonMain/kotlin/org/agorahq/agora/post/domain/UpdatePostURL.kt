package org.agorahq.agora.post.domain

import org.agorahq.agora.core.api.data.ResourceURLWithId
import org.hexworks.cobalt.core.api.UUID

data class UpdatePostURL(
        override val id: UUID,
        override val redirectTo: String? = null,
        override val pageElementToEdit: String? = null
) : ResourceURLWithId<Post> {

    override fun generate() = "/$root/$id/update"

    override fun matches(resource: Post): Boolean {
        return resource.id == id
    }

    companion object {
        const val root = "posts"
        val route = "/$root/{${UpdatePostURL::id.name}}/update"
    }

}
