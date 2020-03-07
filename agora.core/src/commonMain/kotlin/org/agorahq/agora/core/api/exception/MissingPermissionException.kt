package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.security.User

class MissingPermissionException(
        user: User,
        operation: AnyOperation
) : RuntimeException("User $user has no permission to perform operation $operation.")