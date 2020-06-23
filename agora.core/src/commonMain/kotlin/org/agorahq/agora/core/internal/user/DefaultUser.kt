package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.security.Group
import org.agorahq.agora.core.api.security.RoleDescriptor
import org.agorahq.agora.core.api.security.User
import org.hexworks.cobalt.core.api.UUID

data class DefaultUser(
        override val firstName: String,
        override val lastName: String,
        override val email: String,
        override val username: String,
        override val roles: Set<RoleDescriptor>,
        override val groups: Set<Group>,
        override val id: UUID
) : User {

    override fun toString() = User.toString(this)

}
