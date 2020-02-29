package org.agorahq.agora.delivery

import org.agorahq.agora.core.api.DefaultPermissions.*
import org.agorahq.agora.core.api.security.Role

enum class DefaultRoles(
        val role: Role
) {
    ATTENDEE(Role.create(
            name = "Attendee",
            permissions = listOf(CREATE_COMMENT))),
    ADMIN(Role.create(
            name = "Admin",
            permissions = ATTENDEE.role.permissions +
                    setOf(CREATE_POST, DELETE_POST, EDIT_POST, PUBLISH_POST, HIDE_POST)))
}