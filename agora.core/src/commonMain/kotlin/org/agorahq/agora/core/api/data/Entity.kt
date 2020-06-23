package org.agorahq.agora.core.api.data

import org.hexworks.cobalt.core.api.UUID

/**
 * Represents a domain object which can be identified by a unique identifier.
 */
interface Entity {

    val id: UUID
}
