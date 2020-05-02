package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.api.security.RoleDescriptor

class RolesBuilder {

    private val roles = mutableListOf<Role>()

    operator fun RoleDescriptor.invoke(fn: RoleBuilder.() -> Unit): Role {
        val builder = RoleBuilder(this)
        fn(builder)
        return builder.build().apply {
            roles.add(this)
        }
    }

    fun build(): Iterable<Role> = roles.toList()

}