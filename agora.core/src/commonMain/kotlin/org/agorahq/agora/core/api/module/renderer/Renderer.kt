package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext

interface Renderer<R: ContentResource, C: OperationContext> : Operation<R, C, String> {

    override fun C.execute(): String
}