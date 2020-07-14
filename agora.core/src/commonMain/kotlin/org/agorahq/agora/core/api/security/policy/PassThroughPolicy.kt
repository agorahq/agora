package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext

object PassThroughPolicy : Policy<Resource, Any> {

    override fun invoke(context: OperationContext<Any>, element: Resource) = true
}
