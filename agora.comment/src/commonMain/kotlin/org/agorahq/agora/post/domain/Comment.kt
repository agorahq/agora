package org.agorahq.agora.post.domain

import org.agorahq.agora.core.domain.FeatureObject
import org.hexworks.cobalt.Identifier

class Comment(
        val markdownContent: String,
        val author: String,
        override val parentId: Identifier,
        override val id: Identifier,
        override val createdAtMs: Long,
        override val updatedAtMs: Long
) : FeatureObject