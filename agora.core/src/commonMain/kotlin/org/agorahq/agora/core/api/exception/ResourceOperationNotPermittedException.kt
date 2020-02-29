package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.extensions.AnyContentOperation
import org.agorahq.agora.core.api.security.User

class ResourceOperationNotPermittedException(
        user: User,
        operation: AnyContentOperation
) : RuntimeException("Operation $operation is not permitted for user $user.")