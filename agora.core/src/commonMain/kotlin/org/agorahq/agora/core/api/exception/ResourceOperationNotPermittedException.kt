package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.security.User

class ResourceOperationNotPermittedException(
        user: User,
        operation: AnyOperation
) : RuntimeException("Operation $operation is not permitted for user $user.")