package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.domain.document.PageURL
import org.hexworks.cobalt.datatypes.Maybe

interface DocumentQueryService<D : Page> : QueryService<D> {

    fun findByPermalink(pageURL: PageURL<D>): Maybe<D>
}