package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.builder.AuthorizationBuilder

/**
 * [Authorization] represents the authorization settings for a whole application including
 * [Role]s, [Group]s, [Permission]s and policies. Use [AuthorizationBuilder] to create
 * a new [Authorization] object.
 */
interface Authorization {

    /**
     * Tells whether the [currentUser] can perform the given [operation] on an object
     * owned by [owner].
     */
    // TODO: user/group access is irrelevant from the outside world
    // TODO: we should authorize commands, not operations, operations don't have
    // TODO: the context!
    fun hasUserAccess(currentUser: User, owner: User, operation: AnyOperation): Boolean

    /**
     * Tells whether the [currentUser] can perform the given [operation] based on
     * their group(s).
     */
    fun hasGroupAccess(currentUser: User, operation: AnyOperation): Boolean

    /**
     * Tells whether the given [user] can access the given [resource].
     */
    fun <R : Resource> hasResourceAccess(user: User, resource: R): Boolean

    /**
     * Tries to authorize the [currentUser] to perform [operation] on an
     * object owned by [owner]. Throws an exception if [currentUser] is not
     * authorized.
     */
    fun tryAuthorize(currentUser: User, owner: User, operation: AnyOperation)

    /**
     * Tries to authorize the [currentUser] to perform [operation].
     * Throws an exception if [currentUser] is not authorized.
     */
    fun tryAuthorize(currentUser: User, operation: AnyOperation)

}