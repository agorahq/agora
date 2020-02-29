package org.agorahq.agora.core.api.services.impl

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageURL
import org.agorahq.agora.core.api.services.DocumentQueryService
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryDocumentQueryService<D : Page>(
        private val objects: MutableMap<UUID, D>
) : DocumentQueryService<D> {

    override fun findAll(): Sequence<D> = objects.values.asSequence()

    override fun findById(id: UUID) = Maybe.ofNullable(objects[id])

    override fun findByUrl(pageURL: PageURL<D>): Maybe<D> {
        return Maybe.ofNullable(objects.values.firstOrNull { pageURL.matches(it) })
    }
}