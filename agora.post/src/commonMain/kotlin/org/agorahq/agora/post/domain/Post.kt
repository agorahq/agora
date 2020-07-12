package org.agorahq.agora.post.domain

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ContentFormat
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.shared.date.Dates.simpleDateFormat
import org.agorahq.agora.core.internal.document.BuiltInContentFormat.MARKDOWN
import org.hexworks.cobalt.core.api.UUID

class Post(
        val title: String,
        val shortDescription: String,
        val tags: Set<String>,
        val excerpt: String = shortDescription,
        override val owner: User,
        override val content: String,
        override val format: ContentFormat = MARKDOWN,
        override val createdAt: DateTime = DateTime.now(),
        override val updatedAt: DateTime = createdAt,
        override val publishedAt: DateTime = createdAt,
        override val url: PostURL = PostURL(createdAt.format(simpleDateFormat), title),
        override val id: UUID = UUID.randomUUID()
) : Page
