package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.Entity
import org.hexworks.cobalt.core.api.UUID

interface StorageService<E : Entity> {

    fun create(entity: E)

    fun delete(entity: E)

    fun deleteById(id: UUID)
}
