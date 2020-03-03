package org.agorahq.agora.core.api.template

import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.extensions.toSlug

data class PostResourceURL(
        val date: String,
        val titleSlug: String
) : ResourceURL<TestPost> {

    override fun generate() = "/posts/$date/$titleSlug.html"

    override fun matches(resource: TestPost): Boolean {
        return resource.date == date && resource.title.toSlug() == titleSlug
    }

    companion object {
        const val route = "/posts/{date}/{titleSlug}.html"
    }

}