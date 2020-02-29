package org.agorahq.agora.core.api.services.impl

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.services.StorageService
import org.hexworks.cobalt.core.api.UUID

class InMemoryStorageService<E : ContentResource>(
        private val objects: MutableMap<UUID, E>
) : StorageService<E> {

    override fun create(resource: E) {
        objects[resource.id] = resource
    }

    override fun delete(resource: E) {
        objects.remove(resource.id)
    }
}