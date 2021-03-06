package org.agorahq.agora.core.api.template

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ContentFormat
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.document.BuiltInContentFormat.MARKDOWN
import org.hexworks.cobalt.core.api.UUID

class TestPost(
        val title: String,
        val date: String,
        val excerpt: String,
        val shortDescription: String,
        val tags: Set<String>,
        override val owner: User,
        override val id: UUID = UUID.randomUUID(),
        override val content: String,
        override val format: ContentFormat = MARKDOWN,
        override val url: PostResourceURL = PostResourceURL(date, title),
        override val createdAt: DateTime = DateTime.now(),
        override val updatedAt: DateTime = createdAt,
        override val publishedAt: DateTime = createdAt
) : Page
