package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.PageContentResource
import org.agorahq.agora.core.api.document.ContentResource

interface DocumentElementQueryService<E : PageContentResource> : QueryService<E> {

    fun findByParent(parent: ContentResource): Sequence<E>

}