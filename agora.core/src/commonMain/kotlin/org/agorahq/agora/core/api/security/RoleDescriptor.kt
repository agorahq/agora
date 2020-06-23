package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.internal.security.DefaultRoleDescriptor

/**
 * Represents the textual representation of a [Role]. Each [RoleDescriptor]
 * must have an unique [name].
 */
interface RoleDescriptor {

    val name: String

    companion object {

        fun create(name: String): RoleDescriptor = DefaultRoleDescriptor(name)
    }
}
