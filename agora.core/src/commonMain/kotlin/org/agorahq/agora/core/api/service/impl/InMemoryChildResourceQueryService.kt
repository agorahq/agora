package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.PageElement
import org.agorahq.agora.core.api.service.ChildResourceQueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryChildResourceQueryService<E : PageElement>(
        private val objects: MutableMap<UUID, E>
) : ChildResourceQueryService<E> {

    override fun findAll() = objects.values.asSequence()

    override fun findById(id: UUID) = Maybe.ofNullable(objects[id])

    override fun findByParent(parent: Page): Sequence<E> {
        return objects.asSequence()
                .filter { it.value.parentId == parent.id }
                .map { it.value }
    }
}