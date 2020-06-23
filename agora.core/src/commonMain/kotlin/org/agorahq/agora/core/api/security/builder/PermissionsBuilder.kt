package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.operation.context.*
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.policy.GroupFilterPolicy
import org.agorahq.agora.core.api.security.policy.Policy
import org.agorahq.agora.core.api.security.policy.ResourceFilterPolicy
import org.agorahq.agora.core.api.security.policy.UserFilterPolicy
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.internal.user.DefaultPermission
import kotlin.jvm.JvmName

class PermissionsBuilder<R : Resource>() {

    private val permissions = mutableMapOf<OperationDescriptor<out R, out OperationContext, out Any>, MutableList<Policy>>()

    infix fun OperationDescriptor<out R, out OperationContext, out Any>.allowFor(policy: GroupFilterPolicy) = also {
        addPolicy(this, policy)
    }

    infix fun OperationDescriptor<out R, out OperationContext, out Any>.filterFor(policy: ResourceFilterPolicy<R>) = also {
        addPolicy(this, policy)
    }

    @JvmName("allowForResourceContext")
    infix fun OperationDescriptor<out R, out ResourceContext<R>, out Any>.allowFor(policy: UserFilterPolicy) = also {
        addPolicy(this, policy)
    }

    @JvmName("allowForResourceIdContext")
    infix fun OperationDescriptor<out R, out ResourceIdContext, out Any>.allowFor(policy: UserFilterPolicy) = also {
        addPolicy(this, policy)
    }

    @JvmName("allowForViewModelContext")
    infix fun OperationDescriptor<out R, out ViewModelContext<out ViewModel>, out Any>.allowFor(policy: UserFilterPolicy) = also {
        addPolicy(this, policy)
    }

    @JvmName("allowForPageURLContext")
    infix fun OperationDescriptor<out R, out PageURLContext<out Page>, out Any>.allowFor(policy: UserFilterPolicy) = also {
        addPolicy(this, policy)
    }

    @JvmName("allowForPageContext")
    infix fun OperationDescriptor<out R, out PageContext<out Page>, out Any>.allowFor(policy: UserFilterPolicy) = also {
        addPolicy(this, policy)
    }

    fun build(): Iterable<Permission<R>> = permissions.map { (operation, policies) ->
        DefaultPermission(
                name = operation.name,
                operationDescriptor = operation,
                policies = policies)
    }

    private fun addPolicy(descriptor: OperationDescriptor<out R, out OperationContext, out Any>, policy: Policy) {
        permissions.getOrPut(descriptor) { mutableListOf() }.add(policy)
    }


}
