package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.DocumentPart

interface StorageService<E : DocumentPart> {

    fun store(entity: E)

    fun delete(entity: E)
}