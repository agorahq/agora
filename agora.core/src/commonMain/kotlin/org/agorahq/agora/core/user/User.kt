package org.agorahq.markland.core.user

/**
 * Represents a registered person.
 */
class User(
        /**
         * The username (not the First/Last name).
         */
        val name: String,
        val roles: Set<Role>,
        val permissions: Set<Permission>) {

    /**
     * The [Permission]s of this [User] and their [Role]s' combined.
     */
    val effectivePermissions: Set<Permission>
        get() = roles.flatMap { it.permissions }.toSet().plus(permissions)
}