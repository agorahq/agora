package org.agorahq.agora.delivery

import org.agorahq.agora.core.api.data.UserMetadata
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.delivery.security.BuiltInRoles

class Session(
        val userId: String,
        val userEmail: String,
        val userNick: String,
        val role: String
) {

    fun toUser(): User = UserMetadata.create(
            email = userEmail,
            username = userNick,
            roles = setOf(BuiltInRoles.ATTENDEE)).toUser()
}