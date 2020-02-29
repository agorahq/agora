package org.agorahq.agora.core.internal.behavior

import org.agorahq.agora.core.api.behavior.Secured
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.User

class DefaultSecured(
        private val permissions: Set<Permission>
) : Secured {

    override fun canBeAccessedBy(user: User) = user.roles
            .flatMap { it.permissions }
            .containsAll(permissions)
}