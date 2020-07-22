package org.agorahq.agora.post.domain

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ContentFormat
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.extensions.toSlug
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.shared.date.Dates.simpleDateFormat
import org.agorahq.agora.core.internal.document.BuiltInContentFormat.MARKDOWN
import org.hexworks.cobalt.core.api.UUID

data class Post(
        val title: String,
        val abstract: String,
        val tags: Set<String>,
        val excerpt: String = abstract,
        override val owner: User,
        override val content: String,
        override val format: ContentFormat = MARKDOWN,
        override val createdAt: DateTime = DateTime.now(),
        override val updatedAt: DateTime = createdAt,
        override val publishedAt: DateTime = DateTime.fromUnix(Long.MAX_VALUE),
        override val url: ShowPostURL = ShowPostURL(createdAt.format(simpleDateFormat), title.toSlug()),
        override val id: UUID = UUID.randomUUID()
) : Page {

    companion object {

        fun empty(owner: User) = Post(
                title = "",
                abstract = "",
                tags = setOf(),
                content = "",
                owner = owner
        )
    }
}
