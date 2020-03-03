package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL

interface PageRequestContext<P : Page> : OperationContext {

    val url: ResourceURL<P>

    operator fun component3() = url

    companion object

}