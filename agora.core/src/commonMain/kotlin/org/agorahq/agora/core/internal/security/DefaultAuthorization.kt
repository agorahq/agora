package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.exception.MissingPermissionException
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.*
import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.api.security.policy.UserFilterPolicy

data class DefaultAuthorization(
        override val roles: Iterable<Role>,
        override val groups: Iterable<Group>
) : Authorization {

    private val roleLookup = roles.map { (descriptor, permissions) ->
        descriptor to permissions
    }.toMap()

    override fun canExecute(currentUser: User, owner: User, operation: AnyOperation): Boolean {
        return currentUser.fetchPermissions().fetchMatchingPermissions(operation).any { permission ->
            permission.policies.filterIsInstance<UserFilterPolicy>().all { policy ->
                policy(currentUser, owner)
            }
        }
    }

    override fun <R : Resource> canAccess(user: User, resource: R): Boolean {
        return user.fetchPermissions().any { permission ->
            permission.policies.filterIsInstance<ResourceFilterPolicy<R>>().all { policy ->
                policy(resource)
            }
        }
    }

    override fun tryAuthorize(currentUser: User, owner: User, operation: AnyOperation) {
        if (canExecute(currentUser, owner, operation).not()) {
            throw MissingPermissionException(currentUser, operation)
        }
    }

    private fun User.fetchPermissions(): List<Permission<out Resource>> = roles.flatMap {
        roleLookup.getValue(it)
    }

    private fun Iterable<Permission<out Resource>>.fetchMatchingPermissions(operation: AnyOperation) = filter {
        it.operationDescriptor == operation.descriptor
    }
}