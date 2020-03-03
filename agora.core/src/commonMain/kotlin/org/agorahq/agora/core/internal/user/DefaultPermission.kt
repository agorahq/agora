package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.Policy

data class DefaultPermission<R : Resource>(
        override val operation: OperationDescriptor<R, OperationType>,
        override val policies: Iterable<Policy>
) : Permission<R>