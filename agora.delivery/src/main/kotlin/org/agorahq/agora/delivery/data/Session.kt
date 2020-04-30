package org.agorahq.agora.delivery.data

import org.agorahq.agora.core.api.extensions.isAuthenticated
import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.security.Group
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.user.DefaultUser
import org.agorahq.agora.delivery.data.AuthenticationState.LOGGED_IN
import org.agorahq.agora.delivery.data.AuthenticationState.LOGGED_OUT
import org.agorahq.agora.delivery.security.BuiltInRoles

data class Session(
        val email: String,
        val id: String = "",
        val username: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val state: String = LOGGED_OUT.name,
        val roles: Set<String> = setOf(),
        val groups: Set<String> = setOf()) {

    fun toUser(): User = DefaultUser(
            firstName = firstName,
            lastName = lastName,
            email = email,
            username = username,
            roles = roles.map {
                if (it == Role.ANONYMOUS.name) Role.ANONYMOUS else BuiltInRoles.valueOf(it)
            }.toSet(),
            id = id.toUUID(),
            groups = groups.map {
                Group.create(it)
            }.toSet())

    companion object {

        fun fromUser(user: User) = Session(
                email = user.email,
                username = user.username,
                id = user.id.toString(),
                firstName = user.firstName,
                lastName = user.lastName,
                state = if (user.isAuthenticated) LOGGED_IN.name else LOGGED_OUT.name,
                roles = user.roles.map { it.name }.toSet())
    }
}