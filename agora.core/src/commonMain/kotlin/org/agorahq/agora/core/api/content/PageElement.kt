package org.agorahq.agora.core.api.content

import org.agorahq.agora.core.api.resource.Resource
import org.hexworks.cobalt.core.api.UUID

interface PageElement : Resource {

    val parentId: UUID

    companion object
}