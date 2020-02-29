package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.document.ContentResource

interface ResourceListingContext<R : ContentResource> : OperationContext {

    val items: Sequence<R>

    operator fun component3() = items

}