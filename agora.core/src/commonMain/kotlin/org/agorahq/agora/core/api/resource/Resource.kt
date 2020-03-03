package org.agorahq.agora.core.api.resource

import org.agorahq.agora.core.api.content.ContentFormat
import org.agorahq.agora.core.api.data.Entity
import org.agorahq.agora.core.api.security.User

interface Resource : Entity {

    val owner: User

    val format: ContentFormat
    val content: String

    val createdAt: Long
    val updatedAt: Long
    val publishedAt: Long

    companion object
}