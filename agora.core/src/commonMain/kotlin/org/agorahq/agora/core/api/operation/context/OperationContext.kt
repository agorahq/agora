package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User
import kotlin.jvm.JvmStatic

/**
 * Contains the necessary parameters (input) for an [Operation].
 * This interface can be specialized with additional information.
 */
interface OperationContext<I : Any> {

    val site: SiteMetadata
    val user: User
    val authorization: Authorization
    val message: Message?
    val input: I

    operator fun component1() = site

    operator fun component2() = user

    operator fun component3() = authorization

    operator fun component4() = message

    operator fun component5() = input


    fun <I : Any> withInput(input: I): OperationContext<I> = create(
            site = site,
            user = user,
            authorization = authorization,
            message = message,
            input = input
    )

    /**
     * Tells whether the [User] can execute the given [operation] in [this] context.
     */
    infix fun User.can(operation: AnyOperationDescriptor): Boolean

    /**
     * Tells whether the [User] can execute any of the given [operations] in [this] context.
     */
    infix fun User.canDoAnyOf(operations: Iterable<AnyOperationDescriptor>): Boolean

    companion object {

        @JvmStatic
        fun <I : Any> create(
                site: SiteMetadata,
                user: User,
                authorization: Authorization,
                message: Message? = null,
                input: I
        ) = DefaultOperationContext(
                site = site,
                user = user,
                authorization = authorization,
                message = message,
                input = input
        )

    }
}
