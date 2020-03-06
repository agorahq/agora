package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.policy.Policy
import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.api.security.policy.UserFilterPolicy
import org.agorahq.agora.core.internal.user.DefaultPermission

class PermissionsBuilder<R : Resource>() {

    private val permissions = mutableMapOf<OperationDescriptor<out R, out OperationContext, out Any>, MutableList<Policy>>()

    infix fun OperationDescriptor<out R, out OperationContext, out Any>.allowFor(policy: UserFilterPolicy) = also {
        permissions.getOrPut(this) { mutableListOf() }.add(policy)
    }

    infix fun OperationDescriptor<out R, out OperationContext, out Any>.filterFor(policy: ResourceFilterPolicy<R>) = also {
        permissions.getOrPut(this) { mutableListOf() }.add(policy)
    }

    fun build(): Iterable<Permission<R>> = permissions.map { (operation, policies) ->
        DefaultPermission(
                operation = operation,
                policies = policies)
    }


}