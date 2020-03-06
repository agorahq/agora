package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel
import kotlin.jvm.JvmStatic

interface OperationContext {

    val site: SiteMetadata
    val user: User

    operator fun component1() = site

    operator fun component2() = user

    fun <P : Page> toPageURLContext(url: ResourceURL<P>): PageURLContext<P> = PageURLContext(
            site = site,
            user = user,
            url = url)

    fun <M : ViewModel> toViewModelContext(viewModel: M): ViewModelContext<M> = ViewModelContext(
            site = site,
            user = user,
            viewModel = viewModel)

    fun <R : Resource> toResourceContext(resource: R): ResourceContext<R> = ResourceContext(
            site = site,
            user = user,
            resource = resource)

    fun <P : Page> toPageContext(page: P): PageContext<P> = PageContext(
            site = site,
            user = user,
            page = page)

    companion object {

        @JvmStatic
        fun create(site: SiteMetadata, user: User) =
                DefaultOperationContext(
                        site = site,
                        user = user)

    }
}