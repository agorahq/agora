package org.agorahq.agora.core.api.services.impl

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.services.StorageService
import org.hexworks.cobalt.core.api.UUID

class InMemoryStorageService<E : Content>(
        private val objects: MutableMap<UUID, E>
) : StorageService<E> {

    override fun store(entity: E) {
        objects[entity.id] = entity
    }

    override fun delete(entity: E) {
        objects.remove(entity.id)
    }
}