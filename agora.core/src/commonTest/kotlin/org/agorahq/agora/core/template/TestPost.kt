package org.agorahq.agora.core.template

import org.agorahq.agora.core.api.document.ContentFormat
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.internal.document.BuiltInContentFormat.MARKDOWN
import org.hexworks.cobalt.core.api.UUID

class TestPost(
        val title: String,
        val date: String,
        val excerpt: String,
        val shortDescription: String,
        val tags: Set<String>,
        override val id: UUID = UUID.randomUUID(),
        override val content: String,
        override val format: ContentFormat = MARKDOWN,
        override val url: PostResourceURL = PostResourceURL(date, title),
        override val createdAt: Long = 1,
        override val updatedAt: Long = createdAt,
        override val publishedAt: Long = createdAt
) : Page