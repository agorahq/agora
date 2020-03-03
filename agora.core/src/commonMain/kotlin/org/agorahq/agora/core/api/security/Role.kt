package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.resource.Resource

/**
 * [Role] is the primary component of the Role Based Access Control (RBAC) utility.
 */
interface Role {

    val descriptor: RoleDescriptor
    val permissions: Iterable<Permission<out Resource>>

}