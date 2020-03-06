package org.agorahq.agora.core.api.service.impl

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.PageElement
import org.agorahq.agora.core.api.service.PageElementQueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryPageElementQueryService<E : PageElement>(
        private val objects: MutableMap<UUID, E>
) : BaseQueryService<E>(objects), PageElementQueryService<E> {

    override fun findByParent(parent: Page): Sequence<E> {
        return objects.asSequence()
                .filter { it.value.parentId == parent.id }
                .map { it.value }
    }
}