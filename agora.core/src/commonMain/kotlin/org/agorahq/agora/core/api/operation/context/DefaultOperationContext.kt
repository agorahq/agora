package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User

@Suppress("UNCHECKED_CAST")
data class DefaultOperationContext<I : Any>(
        override val site: SiteMetadata,
        override val user: User,
        override val authorization: Authorization,
        override val message: Message? = null,
        override val input: I
) : OperationContext<I> {

    private val context: OperationContext<I>
        get() = this

    override fun User.can(operation: AnyOperationDescriptor): Boolean {
        return authorization.canExecute(context as OperationContext<Any>, operation)
    }

    override fun User.canDoAnyOf(operations: Iterable<AnyOperationDescriptor>): Boolean {
        return operations.fold(false) { acc, next ->
            acc || can(next)
        }
    }
}
