package org.agorahq.agora.core.services.impl

import org.agorahq.agora.core.domain.document.DocumentElement
import org.agorahq.agora.core.domain.document.DocumentPart
import org.agorahq.agora.core.services.DocumentElementQueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryDocumentElementQueryService<E : DocumentElement>(
        private val objects: MutableMap<UUID, E>
) : DocumentElementQueryService<E> {

    override fun findAll() = objects.values.asSequence()

    override fun findById(id: UUID) = Maybe.ofNullable(objects[id])

    override fun findByParent(parent: DocumentPart): Sequence<E> {
        return objects.asSequence()
                .filter { it.value.parentId == parent.id }
                .map { it.value }
    }
}