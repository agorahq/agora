package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.Content

interface StorageService<E : Content> {

    fun store(entity: E)

    fun delete(entity: E)
}