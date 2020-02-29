package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.Content
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

interface QueryService<E : Content> {

    fun findAll(): Sequence<E>

    fun findById(id: UUID): Maybe<E>
}