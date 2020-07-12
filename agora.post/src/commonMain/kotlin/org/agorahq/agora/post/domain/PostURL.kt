package org.agorahq.agora.post.domain

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.extensions.toSlug
import org.agorahq.agora.core.api.shared.date.Dates

data class PostURL(
        val date: DateTime,
        val titleSlug: String
) : ResourceURL<Post> {

    override fun generate() = "$root/${date.format(Dates.simpleDateFormat)}/${titleSlug.toSlug()}.html"

    override fun matches(resource: Post): Boolean {
        return resource.createdAt == date && resource.title.toSlug() == titleSlug
    }

    companion object {
        const val root = "/posts"
        val route = "$root/{${PostURL::date.name}}/{${PostURL::titleSlug.name}}.html"
    }

}
