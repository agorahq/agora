package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource


interface Permission<R : Resource> {

    val operation: OperationDescriptor<R, OperationContext, OperationType<R, OperationContext, Any>>
    val policies: Iterable<Policy>
}