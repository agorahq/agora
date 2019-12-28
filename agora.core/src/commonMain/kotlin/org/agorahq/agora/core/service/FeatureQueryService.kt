package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe

interface FeatureQueryService<D : DomainObject> {

    fun findById(id: Identifier): Maybe<D>

    fun findByParent(parent: DomainObject): Sequence<D>
}