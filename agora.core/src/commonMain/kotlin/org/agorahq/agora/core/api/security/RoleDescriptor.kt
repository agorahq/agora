package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.internal.security.DefaultRoleDescriptor

interface RoleDescriptor {

    val name: String
    val label: String

    companion object {

        fun create(name: String, label: String = name): RoleDescriptor =
                DefaultRoleDescriptor(name, label)
    }
}