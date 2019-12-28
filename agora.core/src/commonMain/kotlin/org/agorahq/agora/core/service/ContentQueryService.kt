package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Permalink
import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe

interface ContentQueryService<D : Document> {

    fun findAll(): Sequence<D>

    fun findById(id: Identifier): Maybe<D>

    fun findByLocation(permalink: Permalink<D>): Maybe<D>
}