package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.security.User

class ResourceAccessNotPermittedException(
        user: User,
        operation: AnyOperation,
        private val msg: String = "User $user has no access to the resource(s) handled by $operation."
) : AuthorizationException(msg) {
    override fun toString() = msg
}
