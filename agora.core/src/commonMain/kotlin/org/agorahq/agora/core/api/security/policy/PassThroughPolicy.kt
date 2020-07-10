package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext

object PassThroughPolicy : Policy<Resource> {

    override fun invoke(context: OperationContext, element: Resource) = true
}
