package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.resource.Resource

interface StorageService<R : Resource> {

    fun create(resource: R)

    fun delete(resource: R)
}