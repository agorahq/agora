package org.agorahq.agora.post.module

import io.ktor.locations.Location
import org.agorahq.agora.core.domain.Permalink
import org.agorahq.agora.post.domain.Post

@Suppress("EXPERIMENTAL_API_USAGE")
@Location("/posts/{date}/{title}.html")
data class PostPermalink(
        val date: String,
        val title: String
) : Permalink<Post> {

    override fun generatePermalink() = "/posts/$date/$title.html"

}