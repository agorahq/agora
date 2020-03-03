package org.agorahq.agora.core.api.module.renderer

import org.agorahq.agora.core.api.module.Command
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource

interface Renderer<R : Resource, C : OperationContext> : Operation<R, C, String> {

    override fun C.reify(): Command<String>
}