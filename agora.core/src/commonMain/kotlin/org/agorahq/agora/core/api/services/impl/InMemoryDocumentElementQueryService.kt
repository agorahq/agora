package org.agorahq.agora.core.api.services.impl

import org.agorahq.agora.core.api.document.PageContent
import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.services.DocumentElementQueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryDocumentElementQueryService<E : PageContent>(
        private val objects: MutableMap<UUID, E>
) : DocumentElementQueryService<E> {

    override fun findAll() = objects.values.asSequence()

    override fun findById(id: UUID) = Maybe.ofNullable(objects[id])

    override fun findByParent(parent: Content): Sequence<E> {
        return objects.asSequence()
                .filter { it.value.parentId == parent.id }
                .map { it.value }
    }
}