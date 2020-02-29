package org.agorahq.agora.core.template

import org.agorahq.agora.core.api.document.PageURL
import org.agorahq.agora.core.api.extensions.toSlug

data class PostPageURL(
        val date: String,
        val titleSlug: String
) : PageURL<Post> {

    override fun generate() = "/posts/$date/$titleSlug.html"

    override fun matches(page: Post): Boolean {
        return page.date == date && page.title.toSlug() == titleSlug
    }

    companion object {
        const val route = "/posts/{date}/{titleSlug}.html"
    }

}