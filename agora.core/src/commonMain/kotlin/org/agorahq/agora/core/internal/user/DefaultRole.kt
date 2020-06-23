package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.Role

class DefaultRole(
        override val name: String,
        override val permissions: Iterable<Permission<out Resource>>
) : Role {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as DefaultRole
        if (name != other.name) return false
        return true
    }

    override fun hashCode() = name.hashCode()

    override fun toString() = "DefaultRole(name='$name', permissions=$permissions)"


}
