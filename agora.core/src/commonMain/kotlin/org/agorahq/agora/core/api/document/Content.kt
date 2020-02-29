package org.agorahq.agora.core.api.document

import org.agorahq.agora.core.api.data.Resource

interface Content : Resource {

    val format: ContentFormat
    val content: String

    val createdAt: Long
    val updatedAt: Long
    val publishedAt: Long

    companion object
}