package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.service.PageElementQueryService
import org.hexworks.cobalt.core.api.UUID

class InMemoryPageElementQueryService<E : PageElement>(
        private val objects: MutableMap<UUID, E>
) : BaseQueryService<E>(objects), PageElementQueryService<E> {

    override fun findByParentId(parentId: UUID): Sequence<E> {
        return objects.asSequence()
                .filter { it.value.parentId == parentId }
                .map { it.value }
    }
}
