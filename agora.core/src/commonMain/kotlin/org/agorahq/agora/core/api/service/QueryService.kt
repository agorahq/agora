package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.Entity
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

interface QueryService<E : Entity> {

    fun findAll(): Sequence<E>

    fun findById(id: UUID): Maybe<E>
}