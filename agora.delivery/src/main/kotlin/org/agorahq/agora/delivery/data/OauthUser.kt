package org.agorahq.agora.delivery.data

import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.delivery.security.BuiltInRoles
import org.hexworks.cobalt.core.api.UUID

interface OauthUser {

    val id: String
    val email: String
    val firstName: String
    val lastName: String


    fun toUser() = User.create(
            email = email,
            username = "",
            id = UUID.fromString(id),
            firstName = firstName,
            lastName = lastName,
            roles = setOf(BuiltInRoles.ATTENDEE),
            groups = setOf())
}