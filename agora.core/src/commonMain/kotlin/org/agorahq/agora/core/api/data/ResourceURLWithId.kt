package org.agorahq.agora.core.api.data

import org.hexworks.cobalt.core.api.UUID

interface ResourceURLWithId<R : Resource> : ResourceURL<R> {

    val id: UUID
}
