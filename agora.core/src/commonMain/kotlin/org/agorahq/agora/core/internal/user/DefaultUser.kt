package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.user.Role
import org.agorahq.agora.core.api.user.User
import org.hexworks.cobalt.core.api.UUID

class DefaultUser(
        override val firstName: String,
        override val lastName: String,
        override val email: String,
        override val username: String,
        override val roles: Set<Role>,
        override val id: UUID
) : User {

    override fun toUser(): User = this

}