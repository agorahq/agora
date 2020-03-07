package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.api.security.RoleDescriptor
import org.agorahq.agora.core.internal.user.DefaultRole
import kotlin.reflect.KClass

class RoleBuilder(private val descriptor: RoleDescriptor) {

    private lateinit var permissions: Iterable<Permission<out Resource>>
    private val parents = mutableListOf<Role>()

    val inherit = Inherit

    infix fun Inherit.from(role: Role) {
        parents.add(role)
    }

    operator fun <R : Resource> KClass<R>.invoke(fn: PermissionsBuilder<R>.() -> Unit) {
        val builder = PermissionsBuilder<R>()
        fn(builder)
        permissions = builder.build()
    }

    fun build(): Role {
        val finalPermissions = parents.map { it.permissions }
                .map { permissions -> permissions.groupBy { it.operationDescriptor } }
                .plus(permissions.groupBy { it.operationDescriptor })
                .fold(mapOf<AnyOperationDescriptor, List<Permission<out Resource>>>()) { acc, next -> acc + next }
                .flatMap { it.value }

        return DefaultRole(
                descriptor = descriptor,
                permissions = finalPermissions)
    }

    object Inherit
}