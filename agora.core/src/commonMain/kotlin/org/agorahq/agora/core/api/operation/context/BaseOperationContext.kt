package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.security.User

abstract class BaseOperationContext : OperationContext {

    private val context: OperationContext
        get() = this

    override fun User.can(operation: AnyOperationDescriptor): Boolean {
        return authorization.canExecute(context, operation)
    }

    override fun User.canDoAnyOf(operations: Iterable<AnyOperationDescriptor>): Boolean {
        return operations.fold(false) { acc, next ->
            acc || can(next)
        }
    }
}
