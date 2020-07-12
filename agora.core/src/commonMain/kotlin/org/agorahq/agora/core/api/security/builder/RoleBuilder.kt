package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.api.security.RoleDescriptor
import org.agorahq.agora.core.internal.user.DefaultRole
import kotlin.reflect.KClass

class RoleBuilder(private val descriptor: RoleDescriptor) {

    private val permissions = mutableListOf<Permission<out Resource>>()
    private val parents = mutableListOf<Role>()

    val inherit = Inherit

    infix fun Inherit.from(role: Role) {
        parents.add(role)
    }

    operator fun <R : Resource> KClass<R>.invoke(fn: PermissionsBuilder<R>.() -> Unit) {
        val builder = PermissionsBuilder<R>()
        fn(builder)
        permissions.addAll(builder.build())
    }

    fun build(): Role {
        val finalPermissions = parents.map { parent -> parent.permissions }
                .map { parentPermissions -> parentPermissions.groupBy { it.operationDescriptor.name } }
                .plus(permissions.groupBy { it.operationDescriptor.name })
                .fold(mapOf<String, List<Permission<out Resource>>>()) { acc, next -> acc + next }
                .flatMap { permission -> permission.value }

        return DefaultRole(
                name = descriptor.name,
                permissions = finalPermissions)
    }

    object Inherit
}
