package org.agorahq.agora.core.api.services

import org.agorahq.agora.core.api.document.PageContent
import org.agorahq.agora.core.api.document.Content

interface DocumentElementQueryService<E : PageContent> : QueryService<E> {

    fun findByParent(parent: Content): Sequence<E>

}