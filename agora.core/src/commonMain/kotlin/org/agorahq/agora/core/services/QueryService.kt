package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.DocumentPart
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

interface QueryService<E : DocumentPart> {

    fun findAll(): Sequence<E>

    fun findById(id: UUID): Maybe<E>
}