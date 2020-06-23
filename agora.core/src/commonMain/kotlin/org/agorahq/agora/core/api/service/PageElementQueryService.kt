package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.data.PageElement
import org.agorahq.agora.core.api.data.Page

interface PageElementQueryService<C : PageElement> : QueryService<C> {

    fun findByParent(parent: Page): Sequence<C>

}
