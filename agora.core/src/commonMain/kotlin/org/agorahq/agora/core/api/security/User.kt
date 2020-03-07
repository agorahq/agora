package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.data.UserMetadata
import org.agorahq.agora.core.internal.user.DefaultUser
import org.hexworks.cobalt.core.api.UUID

interface User : UserMetadata {

    val firstName: String
    val lastName: String
    val roles: Set<RoleDescriptor>
    val groups: Set<Group>

    companion object {

        val ANONYMOUS: User = create(
                email = "anonymous@agorahq.org",
                username = "anonymous",
                roles = setOf(Role.ANONYMOUS),
                groups = setOf(Group.ANONYMOUS)).toUser()

        fun create(
                email: String,
                username: String,
                id: UUID = UUID.randomUUID(),
                firstName: String = "",
                lastName: String = "",
                roles: Set<RoleDescriptor> = setOf(Role.ANONYMOUS),
                groups: Set<Group> = setOf(Group.ANONYMOUS)): User {
            return DefaultUser(
                    id = id,
                    username = username,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    roles = roles,
                    groups = groups)
        }

        fun toString(user: User) = "${user.username}<${user.email}>"
    }
}