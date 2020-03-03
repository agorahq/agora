package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.data.Entity
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.service.QueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryQueryService<E : Entity>(
        private val objects: MutableMap<UUID, E>
) : QueryService<E> {

    override fun findAll(): Sequence<E> = objects.values.asSequence()

    override fun findById(id: UUID) = Maybe.ofNullable(objects[id])

}