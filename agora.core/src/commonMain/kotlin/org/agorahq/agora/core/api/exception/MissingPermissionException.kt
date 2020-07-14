package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.security.User

class MissingPermissionException(
        user: User,
        operation: Operation<out Resource, out Any, out Any>
) : AuthorizationException("User $user has no permission to perform operation $operation.")
