package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.exception.MissingPermissionException
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.security.*
import org.agorahq.agora.core.api.security.policy.GroupFilterPolicy
import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.api.security.policy.UserFilterPolicy

data class DefaultAuthorization(
        private val roles: Iterable<Role>,
        private val groups: Iterable<Group>
) : Authorization {

    private val roleLookup = roles.map { (descriptor, permissions) ->
        descriptor to permissions
    }.toMap()

    override fun hasUserAccess(currentUser: User, owner: User, operation: AnyOperation): Boolean {
        return currentUser.fetchPermissions().fetchMatchingPermissions(operation).any { permission ->
            permission.policies.filterIsInstance<UserFilterPolicy>().all { policy ->
                policy(currentUser, owner)
            }
        }
    }

    override fun hasGroupAccess(currentUser: User, operation: AnyOperation): Boolean {
        return currentUser.fetchPermissions().fetchMatchingPermissions(operation).any { permission ->
            permission.policies.filterIsInstance<GroupFilterPolicy>().all { policy ->
                currentUser.groups.any {
                    policy(it)
                }
            }
        }
    }

    override fun <R : Resource> hasResourceAccess(user: User, resource: R): Boolean {
        return user.fetchPermissions().any { permission ->
            permission.policies.filterIsInstance<ResourceFilterPolicy<R>>().all { policy ->
                policy(resource)
            }
        }
    }

    override fun tryAuthorize(currentUser: User, owner: User, operation: AnyOperation) {
        if (hasUserAccess(currentUser, owner, operation).not()) {
            throw MissingPermissionException(currentUser, operation)
        }
    }

    override fun tryAuthorize(currentUser: User, operation: AnyOperation) {
        if (hasGroupAccess(currentUser, operation).not()) {
            throw MissingPermissionException(currentUser, operation)
        }
    }

    private fun User.fetchPermissions(): List<Permission<out Resource>> = roles.flatMap {
        roleLookup.getOrElse(it.name) { listOf() }
    }

    private fun Iterable<Permission<out Resource>>.fetchMatchingPermissions(operation: AnyOperation) = filter {
        it.operationDescriptor == operation.descriptor
    }
}
