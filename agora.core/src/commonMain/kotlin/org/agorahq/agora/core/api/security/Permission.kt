package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.resource.Resource


interface Permission<R : Resource> {

    val operation: OperationDescriptor<R, OperationType>
    val policies: Iterable<Policy>
}