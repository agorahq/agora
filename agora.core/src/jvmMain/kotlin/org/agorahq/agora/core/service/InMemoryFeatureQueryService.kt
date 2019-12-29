package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.domain.FeatureObject
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import java.util.concurrent.ConcurrentHashMap

class InMemoryFeatureQueryService<F : FeatureObject>(
        objects: Sequence<F>
) : FeatureQueryService<F> {

    private val lookup = ConcurrentHashMap<Identifier, F>()

    init {
        objects.forEach {
            lookup[it.id] = it
        }
    }

    override fun findAll() = lookup.values.asSequence()

    override fun findById(id: Identifier) = Maybe.ofNullable(lookup[id])

    override fun findByParent(parent: DomainObject): Sequence<F> {
        return lookup.asSequence()
                .filter { it.value.parentId == parent.id }
                .map { it.value }
    }
}