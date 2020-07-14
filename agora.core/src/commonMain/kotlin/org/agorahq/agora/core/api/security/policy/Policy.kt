package org.agorahq.agora.core.api.security.policy

import kotlinx.html.I
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.OperationContext

interface Policy<R : Resource, I : Any> {

    operator fun invoke(
            context: OperationContext<I>,
            element: R
    ): Boolean

    companion object {

        fun <R : Resource> create(
                description: String = "anonymous policy",
                fn: (context: OperationContext<I>, element: R) -> Boolean
        ) = object : Policy<R, I> {

            override fun toString() = description

            override fun invoke(context: OperationContext<I>, element: R): Boolean {
                return fn(context, element)
            }

        }
    }
}
