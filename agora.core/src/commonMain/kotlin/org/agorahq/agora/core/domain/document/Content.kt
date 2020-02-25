package org.agorahq.agora.core.domain.document

import org.hexworks.cobalt.core.api.UUID

interface Content {

    val id: UUID
    val format: DocumentContentFormat
    val rawContent: String

}