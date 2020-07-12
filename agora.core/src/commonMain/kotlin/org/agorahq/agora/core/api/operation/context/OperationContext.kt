package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.*
import org.agorahq.agora.core.api.operation.AnyOperationDescriptor
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel
import org.hexworks.cobalt.core.api.UUID
import kotlin.jvm.JvmStatic

/**
 * Contains the necessary metadata (context) for an [Operation].
 * This interface can be specialized with additional information.
 */
interface OperationContext {

    val site: SiteMetadata
    val user: User
    val authorization: Authorization
    val message: Message?

    operator fun component1() = site

    operator fun component2() = user

    operator fun component3() = authorization

    operator fun component4() = message

    fun <P : Page> toPageURLContext(url: ResourceURL<P>): PageURLContext<P> = PageURLContext(
            site = site,
            user = user,
            authorization = authorization,
            message = message,
            url = url)

    fun toResourceIdContext(id: UUID): ResourceIdContext = ResourceIdContext(
            site = site,
            user = user,
            authorization = authorization,
            message = message,
            id = id)

    fun <M : ViewModel> toViewModelContext(viewModel: M): ViewModelContext<M> = ViewModelContext(
            site = site,
            user = user,
            authorization = authorization,
            message = message,
            viewModel = viewModel)

    fun <R : Resource> toResourceContext(resource: R): ResourceContext<R> = ResourceContext(
            site = site,
            user = user,
            authorization = authorization,
            message = message,
            resource = resource)

    fun <P : Page> toPageContext(page: P): PageContext<P> = PageContext(
            site = site,
            user = user,
            message = message,
            authorization = authorization,
            page = page)

    /**
     * Tells whether the [User] can execute the given [operation] in [this] context.
     */
    fun User.canDo(operation: AnyOperationDescriptor): Boolean

    /**
     * Tells whether the [User] can execute any of the given [operations] in [this] context.
     */
    fun User.canDoAnyOf(vararg operations: AnyOperationDescriptor): Boolean

    companion object {

        @JvmStatic
        fun create(
                site: SiteMetadata,
                user: User,
                authorization: Authorization,
                message: Message? = null
        ) = DefaultOperationContext(
                site = site,
                user = user,
                authorization = authorization,
                message = message
        )

    }
}
