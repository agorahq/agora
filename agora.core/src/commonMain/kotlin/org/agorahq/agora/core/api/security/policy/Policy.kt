package org.agorahq.agora.core.api.security.policy

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext

interface Policy<R : Resource> {

    operator fun invoke(
            context: OperationContext,
            element: R
    ): Boolean

    companion object {

        fun <R : Resource> create(
                description: String = "anonymous policy",
                fn: (context: OperationContext, element: R) -> Boolean
        ) = object : Policy<R> {

            override fun toString() = description

            override fun invoke(context: OperationContext, element: R): Boolean {
                return fn(context, element)
            }

        }
    }
}
