package org.agorahq.agora.core.api.fixture

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ContentFormat
import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.document.BuiltInContentFormat
import org.hexworks.cobalt.core.api.UUID

data class Comment(
        override val owner: User,
        override val parentId: UUID,
        override val createdAt: DateTime = DateTime.now(),
        override val updatedAt: DateTime = createdAt,
        override val publishedAt: DateTime = createdAt,
        override val content: String,
        override val format: ContentFormat = BuiltInContentFormat.MARKDOWN,
        override val id: UUID = UUID.randomUUID()
) : PageElement
