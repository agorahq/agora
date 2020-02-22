package org.agorahq.agora.comment.domain

import org.agorahq.agora.core.domain.document.BuiltInDocumentContentFormat.MARKDOWN
import org.agorahq.agora.core.domain.document.DocumentContentFormat
import org.agorahq.agora.core.domain.document.DocumentElement
import org.hexworks.cobalt.core.api.UUID

data class Comment(
        val author: String,
        override val rawContent: String,
        override val format: DocumentContentFormat = MARKDOWN,
        override val parentId: UUID,
        override val id: UUID = UUID.randomUUID()
) : DocumentElement