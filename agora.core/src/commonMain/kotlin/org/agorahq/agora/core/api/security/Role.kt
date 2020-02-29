package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.internal.user.DefaultRole

/**
 * [Role] is the primary component of the Role Based Access Control (RBAC) utility.
 */
interface Role {

    val name: String
    val permissions: Iterable<Permission>

    companion object {

        fun create(name: String, permissions: Iterable<Permission>): Role =
                DefaultRole(name, permissions)
    }
}