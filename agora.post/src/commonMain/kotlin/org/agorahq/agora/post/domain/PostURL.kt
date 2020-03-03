package org.agorahq.agora.post.domain

import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.extensions.toSlug

data class PostURL(
        val date: Long,
        val titleSlug: String
) : ResourceURL<Post> {

    override fun generate() = "$root/$date/${titleSlug.toSlug()}.html"

    override fun matches(resource: Post): Boolean {
        return resource.createdAt == date && resource.title.toSlug() == titleSlug
    }

    companion object {
        const val root = "/posts"
        val route = "$root/{${PostURL::date.name}}/{${PostURL::titleSlug.name}}.html"
    }

}