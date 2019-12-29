package org.agorahq.agora.comment.domain

import org.agorahq.agora.core.domain.FeatureObject
import org.agorahq.agora.core.platform.SystemUtils
import org.hexworks.cobalt.Identifier

class Comment(
        val markdownContent: String,
        val author: String,
        override val parentId: Identifier,
        override val id: Identifier = Identifier.randomIdentifier(),
        override val createdAtMs: Long = SystemUtils.currentTimeMillis(),
        override val updatedAtMs: Long = createdAtMs
) : FeatureObject