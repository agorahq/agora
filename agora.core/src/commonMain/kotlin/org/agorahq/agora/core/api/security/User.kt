package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.data.Entity
import org.agorahq.agora.core.internal.user.DefaultUser
import org.hexworks.cobalt.core.api.UUID

/**
 * Represents a person in the system with their corresponding information
 */
interface User : Entity {

    val email: String
    val username: String
    val firstName: String
    val lastName: String
    val roles: Set<RoleDescriptor>
    val groups: Set<Group>

    companion object {

        val ANONYMOUS: User = create(
                id = UUID.fromString("acdc0c2d-c2f1-4597-b1be-cb9af2a6b2fc"),
                email = "anonymous@agorahq.org",
                username = "anonymous",
                roles = setOf(Role.ANONYMOUS),
                groups = setOf(Group.ANONYMOUS))

        fun create(
                email: String,
                username: String,
                id: UUID = UUID.randomUUID(),
                firstName: String = "",
                lastName: String = "",
                roles: Set<RoleDescriptor> = setOf(Role.ANONYMOUS),
                groups: Set<Group> = setOf(Group.ANONYMOUS)
        ): User = DefaultUser(
                id = id,
                username = username,
                email = email,
                firstName = firstName,
                lastName = lastName,
                roles = roles,
                groups = groups)

        fun toString(user: User) = "${user.username}<${user.email}>"
    }
}
