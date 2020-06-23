package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.data.Resource

/**
 * [Role] is the primary component of the Role Based Access Control (RBAC) utility.
 */
interface Role: RoleDescriptor {

    val permissions: Iterable<Permission<out Resource>>

    operator fun component1(): String = name

    operator fun component2(): Iterable<Permission<out Resource>> = permissions

    companion object {

        val ANONYMOUS = RoleDescriptor.create(
                name = "ANONYMOUS")
    }
}
