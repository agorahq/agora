package org.agorahq.agora.core.domain.document

import org.hexworks.cobalt.core.api.UUID

interface DocumentElement : DocumentPart {

    val parentId: UUID

    companion object
}