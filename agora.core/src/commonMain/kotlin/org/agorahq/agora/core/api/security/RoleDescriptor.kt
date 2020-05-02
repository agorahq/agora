package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.internal.security.DefaultRoleDescriptor

interface RoleDescriptor {

    val name: String

    companion object {

        fun create(name: String): RoleDescriptor = DefaultRoleDescriptor(name)
    }
}