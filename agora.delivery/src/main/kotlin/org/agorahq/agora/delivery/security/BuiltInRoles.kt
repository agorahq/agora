package org.agorahq.agora.delivery.security

import org.agorahq.agora.core.api.security.RoleDescriptor

enum class BuiltInRoles(
        override val label: String
) : RoleDescriptor {
    ATTENDEE("Attendee"),
    ADMIN("Admin")
}