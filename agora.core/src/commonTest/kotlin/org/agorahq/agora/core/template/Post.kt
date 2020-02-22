package org.agorahq.agora.core.template

import org.agorahq.agora.core.domain.document.BuiltInDocumentContentFormat.MARKDOWN
import org.agorahq.agora.core.domain.document.DocumentContentFormat
import org.agorahq.agora.core.domain.document.Page
import org.hexworks.cobalt.core.api.UUID

class Post(
        val title: String,
        val date: String,
        val excerpt: String,
        val shortDescription: String,
        override val id: UUID = UUID.randomUUID(),
        override val rawContent: String,
        override val format: DocumentContentFormat = MARKDOWN,
        override val url: PostPageURL = PostPageURL(date, title)
) : Page