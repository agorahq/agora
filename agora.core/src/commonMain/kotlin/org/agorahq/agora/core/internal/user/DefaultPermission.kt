package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.policy.Policy

data class DefaultPermission<R : Resource>(
        override val name: String,
        override val operationDescriptor: OperationDescriptor<out R, out Any, out Any>,
        override val policies: Iterable<Policy<out R, out Any>>
) : Permission<R> {

    override fun toString() =
            "Permission '$name' enables '$operationDescriptor' operation" +
                    if (policies.toList().isEmpty()) "" else " with policies: ${policies.joinToString()}."

}
