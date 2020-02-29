package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.document.Page

interface PageContentListingContext<P : Page, C : Content> : ContentListingContext<C> {

    val parent: P

    operator fun component4() = parent

}