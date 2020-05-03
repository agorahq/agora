package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.data.Entity
import org.agorahq.agora.core.api.service.StorageService
import org.hexworks.cobalt.core.api.UUID

class InMemoryStorageService<E : Entity>(
        private val objects: MutableMap<UUID, E>
) : StorageService<E> {

    override fun create(entity: E) {
        objects[entity.id] = entity
    }

    override fun delete(entity: E) {
        objects.remove(entity.id)
    }

    override fun deleteById(id: UUID) {
        objects.remove(id)
    }
}