package org.agorahq.agora.core.template

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.platform.SystemUtils
import org.hexworks.cobalt.Identifier

class Post(
        val title: String,
        val date: String,
        val excerpt: String,
        val shortDescription: String,
        override val id: Identifier = Identifier.randomIdentifier(),
        override val markdownContent: String,
        override val permalink: String = "/post/$date-$title.html",
        override val createdAtMs: Long = SystemUtils.currentTimeMillis(),
        override val updatedAtMs: Long = createdAtMs
) : Document