package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.policy.Policy

data class DefaultPermission<R : Resource>(
        override val operation: OperationDescriptor<out R, out OperationContext, out Any>,
        override val policies: Iterable<Policy>
) : Permission<R>