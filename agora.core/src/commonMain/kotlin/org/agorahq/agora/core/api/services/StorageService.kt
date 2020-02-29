package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.ContentResource

interface StorageService<R : ContentResource> {

    fun create(resource: R)

    fun delete(resource: R)
}