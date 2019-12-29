package org.agorahq.agora.core.template

import org.agorahq.agora.core.domain.Permalink

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