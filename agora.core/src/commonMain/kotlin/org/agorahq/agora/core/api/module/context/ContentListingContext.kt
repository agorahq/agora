package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.document.Content

interface ContentListingContext<C : Content> : OperationContext {

    val items: Sequence<C>

    operator fun component3() = items

}