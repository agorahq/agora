package org.agorahq.agora.delivery.data

import org.agorahq.agora.core.api.extensions.toUUID
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.user.DefaultUser
import org.agorahq.agora.delivery.security.BuiltInRoles

data class Session(
        val email: String,
        val id: String = "",
        val username: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val state: String,
        val roles: Set<String> = setOf()) {

    fun toUser(): User = DefaultUser(
            firstName = firstName,
            lastName = lastName,
            email = email,
            username = username,
            roles = roles.map { BuiltInRoles.valueOf(it) }.toSet(),
            id = id.toUUID())

    companion object {

        fun fromUser(user: User) = Session(
                email = user.email,
                username = user.username,
                id = user.id.toString(),
                firstName = user.firstName,
                lastName = user.lastName,
                state = UserState.REGISTERED.name,
                roles = user.roles.map { it.name }.toSet())
    }
}