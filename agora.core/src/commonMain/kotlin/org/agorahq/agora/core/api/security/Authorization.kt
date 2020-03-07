package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.resource.Resource

interface Authorization {

    val roles: Iterable<Role>
    val groups: Iterable<Group>

    fun canExecute(currentUser: User, owner: User, operation: AnyOperation): Boolean

    fun <R: Resource> canAccess(user: User, resource: R): Boolean

    fun tryAuthorize(currentUser: User, owner: User, operation: AnyOperation)

}