package org.agorahq.agora.comment.domain

import org.agorahq.agora.core.internal.document.BuiltInContentFormat.MARKDOWN
import org.agorahq.agora.core.api.document.ContentFormat
import org.agorahq.agora.core.api.document.PageContent
import org.agorahq.agora.core.platform.SystemUtils
import org.hexworks.cobalt.core.api.UUID

data class Comment(
        val author: String,
        override val parentId: UUID,
        override val createdAt: Long = SystemUtils.currentTimeMillis(),
        override val updatedAt: Long = createdAt,
        override val publishedAt: Long = createdAt,
        override val content: String,
        override val format: ContentFormat = MARKDOWN,
        override val id: UUID = UUID.randomUUID()
) : PageContent