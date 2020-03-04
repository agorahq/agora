package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.resource.Resource
import org.hexworks.cobalt.core.api.UUID

interface StorageService<R : Resource> {

    fun create(resource: R)

    fun delete(resource: R)

    fun deleteById(id: UUID)
}