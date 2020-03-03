package org.agorahq.agora.core.api.service

import org.agorahq.agora.core.api.content.PageElement
import org.agorahq.agora.core.api.content.Page

interface ChildResourceQueryService<C : PageElement> : QueryService<C> {

    fun findByParent(parent: Page): Sequence<C>

}