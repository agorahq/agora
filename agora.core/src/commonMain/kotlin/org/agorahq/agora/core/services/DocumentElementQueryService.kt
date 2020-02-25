package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.PageContent
import org.agorahq.agora.core.domain.document.Content

interface DocumentElementQueryService<E : PageContent> : QueryService<E> {

    fun findByParent(parent: Content): Sequence<E>

}