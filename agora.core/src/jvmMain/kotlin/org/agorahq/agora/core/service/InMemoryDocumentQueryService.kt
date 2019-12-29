package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Permalink
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import java.util.concurrent.ConcurrentHashMap

class InMemoryDocumentQueryService<D : Document>(
        objects: Sequence<D>
) : DocumentQueryService<D> {

    private val lookup = ConcurrentHashMap<Identifier, D>()

    init {
        objects.forEach {
            lookup[it.id] = it
        }
    }

    override fun findAll(): Sequence<D> = lookup.values.asSequence()

    override fun findById(id: Identifier) = Maybe.ofNullable(lookup[id])

    override fun findByPermalink(permalink: Permalink<D>): Maybe<D> {
        return Maybe.ofNullable(lookup.values.firstOrNull { permalink.matches(it) })
    }
}