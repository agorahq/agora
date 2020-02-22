package org.agorahq.agora.post.domain

import org.agorahq.agora.core.domain.document.BuiltInDocumentContentFormat.MARKDOWN
import org.agorahq.agora.core.domain.document.DocumentContentFormat
import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.post.module.PostPageURL
import org.hexworks.cobalt.core.api.UUID

class Post(
        val title: String,
        val date: String,
        val shortDescription: String,
        val tldr: String = shortDescription,
        override val rawContent: String,
        override val format: DocumentContentFormat = MARKDOWN,
        override val url: PostPageURL = PostPageURL(date, title),
        override val id: UUID = UUID.randomUUID()
) : Page