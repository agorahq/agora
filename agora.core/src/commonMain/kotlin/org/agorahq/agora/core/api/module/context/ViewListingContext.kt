package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.view.ViewModel

interface ViewListingContext<M : ViewModel> : OperationContext {

    val items: Sequence<M>

    operator fun component3() = items

}