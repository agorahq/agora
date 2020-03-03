package org.agorahq.agora.core.api.resource

import org.agorahq.agora.core.api.module.Command
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.ResourceContext

interface ResourceOperation<R : Resource> : Operation<R, ResourceContext<R>, Unit> {

    override fun ResourceContext<R>.reify(): Command<Unit>
}