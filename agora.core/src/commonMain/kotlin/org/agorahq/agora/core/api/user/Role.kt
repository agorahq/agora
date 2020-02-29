package org.agorahq.agora.core.api.user

import org.agorahq.agora.core.internal.user.DefaultRole

interface Role {

    val name: String
    val permissions: Iterable<Permission>

    companion object {

        fun create(name: String, permissions: Iterable<Permission>): Role =
                DefaultRole(name, permissions)
    }
}