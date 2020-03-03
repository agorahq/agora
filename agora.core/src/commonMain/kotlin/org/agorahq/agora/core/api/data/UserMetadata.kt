package org.agorahq.agora.core.api.data

import org.agorahq.agora.core.api.security.RoleDescriptor
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.user.DefaultUser
import org.hexworks.cobalt.core.api.UUID

/**
 * Contains the *essentials* which are required for every user in the system.
 */
interface UserMetadata : Entity {

    val email: String
    val username: String

    fun toUser(): User

    companion object {

        val ANONYMOUS: User = create(
                email = "anonymous@anonymous.com",
                username = "anonymous").toUser()

        fun create(
                email: String,
                username: String,
                roles: Set<RoleDescriptor> = setOf()): UserMetadata {
            return DefaultUser(
                    id = UUID.randomUUID(),
                    username = username,
                    email = email,
                    firstName = "",
                    lastName = "",
                    roles = roles)
        }
    }
}