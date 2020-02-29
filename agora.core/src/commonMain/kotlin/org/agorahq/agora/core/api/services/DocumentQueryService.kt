package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.ResourceURL
import org.hexworks.cobalt.datatypes.Maybe

interface DocumentQueryService<D : Page> : QueryService<D> {

    fun findByUrl(resourceURL: ResourceURL<D>): Maybe<D>
}