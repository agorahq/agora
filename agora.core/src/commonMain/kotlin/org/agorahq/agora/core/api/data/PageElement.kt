package org.agorahq.agora.core.api.data

import org.hexworks.cobalt.core.api.UUID

/**
 * A [PageElement] is a [Resource] which is part of a [Page].
 */
interface PageElement : Resource {

    val parentId: UUID

    companion object
}
