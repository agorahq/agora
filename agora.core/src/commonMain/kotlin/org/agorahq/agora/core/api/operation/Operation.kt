package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType

interface Operation<R : Resource, C : OperationContext, T : Any> : OperationDescriptor<R, C, T> {

    val descriptor: OperationDescriptor<R, C, T>

    fun C.createCommand(): Command<T>
}
