package org.agorahq.agora.core.service.impl

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.service.StorageService
import org.hexworks.cobalt.Identifier

class InMemoryStorageService<D : DomainObject>(
        private val objects: MutableMap<Identifier, D>
) : StorageService<D> {

    override fun store(domainObject: D) {
        objects[domainObject.id] = domainObject
    }

    override fun delete(domainObject: D) {
        objects.remove(domainObject.id)
    }
}