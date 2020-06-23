package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.data.ResourceURL
import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel
import kotlin.jvm.JvmStatic
import org.agorahq.agora.core.api.operation.Operation

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
