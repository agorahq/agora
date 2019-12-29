package org.agorahq.agora.core.service.impl

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.domain.FeatureObject
import org.agorahq.agora.core.service.FeatureQueryService
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe

class InMemoryFeatureQueryService<F : FeatureObject>(
        private val objects: MutableMap<Identifier, F>
) : FeatureQueryService<F> {

    override fun findAll() = objects.values.asSequence()

    override fun findById(id: Identifier) = Maybe.ofNullable(objects[id])

    override fun findByParent(parent: DomainObject): Sequence<F> {
        return objects.asSequence()
                .filter { it.value.parentId == parent.id }
                .map { it.value }
    }
}