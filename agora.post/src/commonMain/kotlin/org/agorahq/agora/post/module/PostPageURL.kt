package org.agorahq.agora.post.module

import org.agorahq.agora.core.domain.document.PageURL
import org.agorahq.agora.post.domain.Post

data class PostPageURL(
        val date: String,
        val title: String
) : PageURL<Post> {

    override fun generate() = "/posts/$date/$title.html"

    override fun matches(document: Post): Boolean {
        return document.date == date && document.title == title
    }

    companion object {
        const val route = "/posts/{date}/{title}.html"
    }

}