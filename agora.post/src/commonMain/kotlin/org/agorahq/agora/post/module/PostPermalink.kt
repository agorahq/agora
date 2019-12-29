package org.agorahq.agora.post.module

import org.agorahq.agora.core.domain.Permalink
import org.agorahq.agora.post.domain.Post

data class PostPermalink(
        val date: String,
        val title: String
) : Permalink<Post> {

    override fun generatePermalink() = "/posts/$date/$title.html"

    override fun matches(document: Post): Boolean {
        return document.date == date && document.title == title
    }

    companion object {
        const val route = "/posts/{date}/{title}.html"
    }

}