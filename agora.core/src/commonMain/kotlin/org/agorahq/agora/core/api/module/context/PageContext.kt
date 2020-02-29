package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.ResourceURL

interface PageContext<P : Page> : OperationContext {

    val url: ResourceURL<P>

    operator fun component3() = url

    companion object

}