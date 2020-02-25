package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.Content

interface StorageService<E : Content> {

    fun store(entity: E)

    fun delete(entity: E)
}