package org.agorahq.agora.post.domain

import org.agorahq.agora.core.api.data.ResourceURL

data class EditPostURL(
        val id: String,
        override val redirectTo: String? = null,
        override val pageElementsToEdit: Iterable<String> = listOf()
) : ResourceURL<Post> {

    override fun generate() = route.replace("{${EditPostURL::id.name}}", id)

    override fun matches(resource: Post): Boolean {
        return resource.id.toString() == id
    }

    companion object {
        val route = "/post/{${EditPostURL::id.name}}/edit"
    }

}
