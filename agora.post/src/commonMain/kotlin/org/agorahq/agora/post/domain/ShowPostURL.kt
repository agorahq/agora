package org.agorahq.agora.post.domain

import kotlinx.serialization.Serializable
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.extensions.toSlug
import org.agorahq.agora.core.api.shared.date.Dates

@Serializable
data class ShowPostURL(
        val date: String,
        val titleSlug: String,
        override val redirectTo: String? = null,
        override val pageElementToEdit: String? = null
) : ResourceURL<Post> {

    override fun generate() = "$root/$date/${titleSlug.toSlug()}.html"

    override fun matches(resource: Post): Boolean {
        return resource.createdAt.format(Dates.simpleDateFormat) == date && resource.title.toSlug() == titleSlug
    }

    companion object {
        const val root = "/posts"
        val route = "$root/{${ShowPostURL::date.name}}/{${ShowPostURL::titleSlug.name}}.html"
    }

}
