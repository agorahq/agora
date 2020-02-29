package org.agorahq.agora.core.api.behavior

import org.agorahq.agora.core.api.user.Permission
import org.agorahq.agora.core.api.user.User
import org.agorahq.agora.core.internal.behavior.DefaultSecured

/**
 * Represents a resource or an operation which is *secured*. Being secured means
 * that the object can only be accessed / executed if the given [User] has the
 * necessary permissions.
 */
interface Secured {

    /**
     * Tells whether the [user] can access the [Secured] object.
     */
    fun canBeAccessedBy(user: User): Boolean

    companion object {

        fun permissions(vararg permissions: Permission): Secured = DefaultSecured(permissions.toSet())
    }
}