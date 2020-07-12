package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.data.*
import org.agorahq.agora.core.api.exception.AuthorizationException
import org.agorahq.agora.core.api.exception.MissingPermissionException
import org.agorahq.agora.core.api.exception.ResourceAccessNotPermittedException
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.security.policy.Policy

@Suppress("UNCHECKED_CAST")
data class DefaultAuthorization(
        private val roles: Iterable<Role>
) : Authorization {

    private val roleLookup: Map<String, List<Permission<out Resource>>> = roles.map { role ->
        role.name to role.permissions.toList()
    }.toMap()


    override fun <C : OperationContext> canExecute(
            context: C,
            operationDescriptor: AnyOperationDescriptor
    ) = context.user.permissions.any {
        it.operationDescriptor.name == operationDescriptor.name
    }

    @Suppress("UNCHECKED_CAST")
    override fun <R : Resource, C : OperationContext, T : Any> authorize(
            context: C,
            operation: Operation<R, C, T>
    ): Result<out Command<T>, out AuthorizationException> {
        val user = context.user
        val permissions = user.permissions
        return permissions.firstOrNull {
            it.operationDescriptor.name == operation.name
        }?.let { (_, _, policies) ->
            with(operation) {
                val data = context.fetchData()
                val filteredData = policies.fold(data) { remaining, next ->
                    next as Policy<R>
                    remaining.filter { next(context, it) }
                }
                when (data) {
                    EmptyElementSource -> Result.Failure(ResourceAccessNotPermittedException(user, operation))
                    is SingleElementSource -> when (filteredData) {
                        EmptyElementSource -> Result.Failure(ResourceAccessNotPermittedException(user, operation))
                        is SingleElementSource -> Result.Success(context.createCommand(filteredData))
                        is MultipleElementSource -> Result.Success(context.createCommand(filteredData))
                    }
                    is MultipleElementSource -> Result.Success(context.createCommand(filteredData))
                }
            }
        } ?: Result.Failure(MissingPermissionException(user, operation))
    }

    private val User.permissions: List<Permission<out Resource>>
        get() = roleLookup.keys.intersect(roles.map { it.name }).flatMap { roleLookup.getValue(it) }
}
