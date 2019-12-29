package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject

interface StorageService<D : DomainObject> {

    fun store(domainObject: D)

    fun delete(domainObject: D)
}