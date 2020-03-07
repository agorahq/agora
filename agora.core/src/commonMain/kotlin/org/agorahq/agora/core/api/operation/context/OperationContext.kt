package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel
import kotlin.jvm.JvmStatic

interface OperationContext {

    val site: SiteMetadata
    val user: User
    val authorization: Authorization

    operator fun component1() = site

    operator fun component2() = user

    operator fun component3() = authorization

    fun <P : Page> toPageURLContext(url: ResourceURL<P>): PageURLContext<P> = PageURLContext(
            site = site,
            user = user,
            authorization = authorization,
            url = url)

    fun <M : ViewModel> toViewModelContext(viewModel: M): ViewModelContext<M> = ViewModelContext(
            site = site,
            user = user,
            authorization = authorization,
            viewModel = viewModel)

    fun <R : Resource> toResourceContext(resource: R): ResourceContext<R> = ResourceContext(
            site = site,
            user = user,
            authorization = authorization,
            resource = resource)

    fun <P : Page> toPageContext(page: P): PageContext<P> = PageContext(
            site = site,
            user = user,
            authorization = authorization,
            page = page)

    companion object {

        @JvmStatic
        fun create(site: SiteMetadata, user: User, authorization: Authorization) =
                DefaultOperationContext(
                        site = site,
                        user = user,
                        authorization = authorization)

    }
}