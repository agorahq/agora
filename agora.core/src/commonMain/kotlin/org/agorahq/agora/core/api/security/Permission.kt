package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.policy.Policy

/**
 * A [Permission] contains [policies] which are necessary to determine whether a given
 * [User] can execute an [Operation] or not.
 */
interface Permission<R : Resource> : PermissionDescriptor {

    val operationDescriptor: OperationDescriptor<out R, out OperationContext, out Any>
    val policies: Iterable<Policy>

    operator fun component2() = operationDescriptor

    operator fun component3() = policies
}
