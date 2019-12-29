package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe

interface QueryService<D : DomainObject> {

    fun findAll(): Sequence<D>

    fun findById(id: Identifier): Maybe<D>
}