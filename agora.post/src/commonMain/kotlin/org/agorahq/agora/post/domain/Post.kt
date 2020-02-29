package org.agorahq.agora.post.domain

import org.agorahq.agora.core.internal.document.BuiltInContentFormat.MARKDOWN
import org.agorahq.agora.core.api.document.ContentFormat
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.platform.SystemUtils
import org.agorahq.agora.post.module.PostPageURL
import org.hexworks.cobalt.core.api.UUID

class Post(
        val title: String,
        val shortDescription: String,
        val excerpt: String = shortDescription,
        override val content: String,
        override val format: ContentFormat = MARKDOWN,
        override val createdAt: Long = SystemUtils.currentTimeMillis(),
        override val updatedAt: Long = createdAt,
        override val publishedAt: Long = createdAt,
        override val url: PostPageURL = PostPageURL(createdAt, title),
        override val id: UUID = UUID.randomUUID()
) : Page