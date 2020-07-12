package org.agorahq.agora.core.api.data

import com.soywiz.klock.DateTime
import org.agorahq.agora.core.api.security.User

/**
 * A [Resource] is an [Entity] which has textual [content] and an [owner]
 * with some additional metadata. [Page]s and [PageElement]s are common
 * [Resource] types.
 */
interface Resource : Entity {

    val owner: User

    val format: ContentFormat
    val content: String

    val createdAt: DateTime
    val updatedAt: DateTime
    val publishedAt: DateTime

    companion object
}
