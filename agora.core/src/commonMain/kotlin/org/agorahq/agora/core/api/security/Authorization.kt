package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.resource.Resource

interface Authorization {

    fun hasUserAccess(currentUser: User, owner: User, operation: AnyOperation): Boolean

    fun hasGroupAccess(currentUser: User, operation: AnyOperation): Boolean

    fun <R : Resource> hasResourceAccess(user: User, resource: R): Boolean

    fun tryAuthorize(currentUser: User, owner: User, operation: AnyOperation)

    fun tryAuthorize(currentUser: User, operation: AnyOperation)

}