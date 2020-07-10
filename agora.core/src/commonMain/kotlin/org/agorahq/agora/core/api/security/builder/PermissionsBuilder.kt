package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.OperationDescriptor
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.policy.PassThroughPolicy
import org.agorahq.agora.core.api.security.policy.Policy
import org.agorahq.agora.core.internal.user.DefaultPermission

@Suppress("UNUSED_PARAMETER")
class PermissionsBuilder<R : Resource> {

    private val permissions = mutableMapOf<OperationDescriptor<out R, out OperationContext, out Any>, MutableList<Policy<out R>>>()

    infix fun OperationDescriptor<out R, out OperationContext, out Any>.allow(policy: PassThroughPolicy) {
        permissions.getOrPut(this) { mutableListOf() }
    }

    infix fun OperationDescriptor<out R, out OperationContext, out Any>.withPolicy(policy: Policy<R>) = also {
        addPolicy(this, policy)
    }

    fun build(): Iterable<Permission<R>> = permissions.map { (operation, policies) ->
        DefaultPermission(
                name = operation.name,
                operationDescriptor = operation,
                policies = policies)
    }


    private fun addPolicy(descriptor: OperationDescriptor<out R, out OperationContext, out Any>, policy: Policy<out R>) {
        permissions.getOrPut(descriptor) { mutableListOf() }.add(policy)
    }


}
