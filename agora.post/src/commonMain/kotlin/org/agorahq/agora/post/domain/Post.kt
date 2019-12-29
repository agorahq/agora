package org.agorahq.agora.post.domain

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.platform.SystemUtils
import org.agorahq.agora.post.module.PostPermalink
import org.hexworks.cobalt.Identifier

class Post(
        val title: String,
        val date: String,
        override val markdownContent: String,
        val shortDescription: String,
        val tldr: String = shortDescription,
        override val permalink: PostPermalink = PostPermalink(date, title),
        override val id: Identifier = Identifier.randomIdentifier(),
        override val createdAtMs: Long = SystemUtils.currentTimeMillis(),
        override val updatedAtMs: Long = createdAtMs
) : Document