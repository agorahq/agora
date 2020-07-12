package org.agorahq.agora.core.api

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.data.ContentFormat
import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.document.BuiltInContentFormat
import org.hexworks.cobalt.core.api.UUID

class Item(
        val inStock: Boolean,
        override val owner: User,
        val price: Long = 1,
        override val id: UUID = UUID.randomUUID(),
        override val url: ResourceURL<out Page> = ItemURL(id),
        override val format: ContentFormat = BuiltInContentFormat.MARKDOWN,
        override val content: String = "",
        override val createdAt: DateTime = DateTime.now(),
        override val updatedAt: DateTime = createdAt,
        override val publishedAt: DateTime = createdAt
) : Page
