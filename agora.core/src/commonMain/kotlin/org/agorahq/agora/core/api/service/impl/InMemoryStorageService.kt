package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.service.StorageService
import org.hexworks.cobalt.core.api.UUID

class InMemoryStorageService<E : Resource>(
        private val objects: MutableMap<UUID, E>
) : StorageService<E> {

    override fun create(resource: E) {
        objects[resource.id] = resource
    }

    override fun delete(resource: E) {
        objects.remove(resource.id)
    }

    override fun deleteById(id: UUID) {
        objects.remove(id)
    }
}