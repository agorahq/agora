package org.agorahq.agora.core.api.document

import org.hexworks.cobalt.core.api.UUID

interface PageContentResource : ContentResource {

    val parentId: UUID

    companion object
}