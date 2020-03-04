package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType

interface Operation<R : Resource, C : OperationContext, T : OperationType<R, C, U>, U : Any> : OperationDescriptor<R, C, U> {

    fun C.createCommand(): Command<U>
}
