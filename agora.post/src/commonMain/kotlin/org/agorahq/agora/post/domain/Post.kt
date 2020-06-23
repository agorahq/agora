package org.agorahq.agora.post.domain

import org.agorahq.agora.core.internal.document.BuiltInContentFormat.MARKDOWN
import org.agorahq.agora.core.api.data.ContentFormat
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.platform.SystemUtils
import org.hexworks.cobalt.core.api.UUID

class Post(
        val title: String,
        val shortDescription: String,
        val tags: Set<String>,
        val excerpt: String = shortDescription,
        override val owner: User,
        override val content: String,
        override val format: ContentFormat = MARKDOWN,
        override val createdAt: Long = SystemUtils.currentTimeMillis(),
        override val updatedAt: Long = createdAt,
        override val publishedAt: Long = createdAt,
        override val url: PostURL = PostURL(createdAt, title),
        override val id: UUID = UUID.randomUUID()
) : Page
