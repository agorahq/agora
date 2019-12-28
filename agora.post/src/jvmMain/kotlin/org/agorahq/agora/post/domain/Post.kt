package org.agorahq.agora.post.domain

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.post.module.PostPermalink
import org.hexworks.cobalt.Identifier

class Post(
        val title: String,
        val date: String,
        val excerpt: String,
        val shortDescription: String,
        val comments: Sequence<Comment>,
        override val permalink: String = PostPermalink(date, title).generatePermalink(),
        override val id: Identifier,
        override val markdownContent: String,
        override val createdAtMs: Long,
        override val updatedAtMs: Long
) : Document