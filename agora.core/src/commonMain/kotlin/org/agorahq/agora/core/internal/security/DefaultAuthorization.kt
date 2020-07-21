package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.data.*
import org.agorahq.agora.core.api.exception.EntityNotFoundException
import org.agorahq.agora.core.api.exception.MissingPermissionException
import org.agorahq.agora.core.api.exception.ResourceAccessNotPermittedException
import org.agorahq.agora.core.api.operation.AnyOperation
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.operation.Command
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.Permission
import org.agorahq.agora.core.api.security.Role
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.security.policy.Policy
import org.hexworks.cobalt.logging.api.LoggerFactory

@Suppress("UNCHECKED_CAST")
data class DefaultAuthorization(
        private val roles: Iterable<Role>
) : Authorization {

    private val logger = LoggerFactory.getLogger(Authorization::class)

    private val roleLookup: Map<String, List<Permission<out Resource>>> = roles.map { role ->
        role.name to role.permissions.toList()
    }.toMap()

    override fun canExecute(
            context: OperationContext<Any>,
            operation: AnyOperationDescriptor
    ): Boolean {
        val user = context.user
        val permissions = user.permissions
        return permissions.any {
            it.operationDescriptor.name == operation.name
        }
    }

    override fun <O : Any> authorizeAny(
            context: OperationContext<out Any>,
            operation: Operation<Resource, Any, O>
    ): Result<out Command<O>, out Exception> {
        logger.info("Trying to authorize $operation for ${context.user}")
        val user = context.user
        val permissions = user.permissions
        return permissions.firstOrNull {
            it.operationDescriptor.name == operation.name
        }?.let { (_, _, policies) ->
            policies as Iterable<Policy<Resource, Any>>
            val data = operation.fetchResource(context)
            val filteredData = policies.fold(data) { remaining, next ->
                remaining.filter { next(context as OperationContext<Any>, it) }
            }
            val result = when (data) {
                EmptyElementSource -> Result.Failure(EntityNotFoundException(
                        type = operation.resourceClass,
                        key = context.input.toString()
                ))
                is SingleElementSource -> when (filteredData) {
                    EmptyElementSource -> Result.Failure(ResourceAccessNotPermittedException(user, operation as AnyOperation))
                    is SingleElementSource -> Result.Success(operation.createCommand(context, filteredData))
                    is MultipleElementSource -> Result.Success(operation.createCommand(context, filteredData))
                }
                is MultipleElementSource -> Result.Success(operation.createCommand(context, filteredData))
            }
            logger.info("Authorization result is: $result")
            result
        } ?: run {
            val e = MissingPermissionException(user, operation as AnyOperation)
            logger.info("Authorization failed: $e")
            Result.Failure(e)
        }
    }


    private val User.permissions: List<Permission<out Resource>>
        get() = roleLookup.keys.intersect(roles.map { it.name }).flatMap { roleLookup.getValue(it) }
}
