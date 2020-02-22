package org.agorahq.agora.core.services.impl

import org.agorahq.agora.core.domain.document.DocumentPart
import org.agorahq.agora.core.services.StorageService
import org.hexworks.cobalt.core.api.UUID

class InMemoryStorageService<E : DocumentPart>(
        private val objects: MutableMap<UUID, E>
) : StorageService<E> {

    override fun store(entity: E) {
        objects[entity.id] = entity
    }

    override fun delete(entity: E) {
        objects.remove(entity.id)
    }
}