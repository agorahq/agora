package org.agorahq.agora.core.api.module.operation

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext

interface PageContentListRenderer<C : Content> : Operation {

    fun render(context: OperationContext, parent: Page): String

}