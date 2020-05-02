package org.agorahq.agora.delivery.data

import io.ktor.auth.Principal
import kotlinx.serialization.Serializable
import org.agorahq.agora.core.api.security.Group
import org.agorahq.agora.core.api.security.RoleDescriptor
import org.agorahq.agora.core.api.security.User
import org.hexworks.cobalt.core.api.UUID

@Serializable
data class UserPrincipal(
        val firstName: String,
        val lastName: String,
        val roles: Set<String>,
        val groups: Set<String>,
        val email: String,
        val username: String,
        val id: String
) : Principal {

    fun toUser(): User = User.create(
            email = email,
            username = username,
            id = UUID.fromString(id),
            firstName = firstName,
            lastName = lastName,
            roles = roles.map { RoleDescriptor.create(it) }.toSet(),
            groups = groups.map { Group.create(it) }.toSet())

    companion object {

        fun fromUser(user: User) = UserPrincipal(
                firstName = user.firstName,
                lastName = user.lastName,
                roles = user.roles.map { it.name }.toSet(),
                groups = user.groups.map { it.name }.toSet(),
                email = user.email,
                username = user.username,
                id = user.id.toString())
    }
}