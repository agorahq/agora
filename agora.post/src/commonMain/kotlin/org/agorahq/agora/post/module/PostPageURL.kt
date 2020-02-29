package org.agorahq.agora.post.module

import org.agorahq.agora.core.api.document.PageURL
import org.agorahq.agora.core.api.extensions.toSlug
import org.agorahq.agora.post.domain.Post

data class PostPageURL(
        val date: Long,
        val titleSlug: String
) : PageURL<Post> {

    override fun generate() = "/posts/$date/${titleSlug.toSlug()}.html"

    override fun matches(page: Post): Boolean {
        return page.createdAt == date && page.title.toSlug() == titleSlug
    }

    companion object {
        val route = "/posts/{${PostPageURL::date.name}}/{${PostPageURL::titleSlug.name}}.html"
    }

}