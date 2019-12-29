package org.agorahq.agora.core.service.impl

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Permalink
import org.agorahq.agora.core.service.DocumentQueryService
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryDocumentQueryService<D : Document>(
        private val objects: MutableMap<Identifier, D>
) : DocumentQueryService<D> {

    override fun findAll(): Sequence<D> = objects.values.asSequence()

    override fun findById(id: Identifier) = Maybe.ofNullable(objects[id])

    override fun findByPermalink(permalink: Permalink<D>): Maybe<D> {
        return Maybe.ofNullable(objects.values.firstOrNull { permalink.matches(it) })
    }
}