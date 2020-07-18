package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.data.Page
import org.hexworks.cobalt.core.api.UUID

interface PageElementQueryService<C : PageElement> : QueryService<C> {

    fun findByParentId(parentId: UUID): Sequence<C>

}
