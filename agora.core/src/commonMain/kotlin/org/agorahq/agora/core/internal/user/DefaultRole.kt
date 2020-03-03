package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.api.security.RoleDescriptor

class DefaultRole(
        override val descriptor: RoleDescriptor,
        override val permissions: Iterable<Permission<out Resource>>
) : Role {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as DefaultRole
        if (descriptor != other.descriptor) return false
        return true
    }

    override fun hashCode() = descriptor.hashCode()

    override fun toString() = "DefaultRole(name='$descriptor', permissions=$permissions)"


}