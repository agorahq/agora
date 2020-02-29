package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageURL
import org.hexworks.cobalt.datatypes.Maybe

interface DocumentQueryService<D : Page> : QueryService<D> {

    fun findByUrl(pageURL: PageURL<D>): Maybe<D>
}