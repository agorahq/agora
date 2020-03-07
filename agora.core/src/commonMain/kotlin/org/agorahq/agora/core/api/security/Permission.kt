package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.policy.Policy


interface Permission<R : Resource> {

    val operationDescriptor: OperationDescriptor<out R, out OperationContext, out Any>
    val policies: Iterable<Policy>

    operator fun component1() = operationDescriptor

    operator fun component2() = policies
}