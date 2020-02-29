package org.agorahq.agora.core.api.module.operation

import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext

interface PageListRenderer<P : Page> : Operation {

    val route: String

    fun render(context: OperationContext): String

}