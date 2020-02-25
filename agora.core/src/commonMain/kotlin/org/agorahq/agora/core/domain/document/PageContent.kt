package org.agorahq.agora.core.domain.document

import org.hexworks.cobalt.core.api.UUID

interface PageContent : Content {

    val parentId: UUID

    companion object
}