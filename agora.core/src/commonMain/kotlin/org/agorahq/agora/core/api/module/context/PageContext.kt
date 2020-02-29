package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.document.Page

interface PageContext<P : Page> : OperationContext {

    val page: P

    operator fun component3() = page

    companion object

}