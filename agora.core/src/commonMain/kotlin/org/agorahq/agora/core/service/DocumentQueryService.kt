package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Permalink
import org.hexworks.cobalt.datatypes.Maybe

interface DocumentQueryService<D : Document> : QueryService<D> {

    fun findByPermalink(permalink: Permalink<D>): Maybe<D>
}