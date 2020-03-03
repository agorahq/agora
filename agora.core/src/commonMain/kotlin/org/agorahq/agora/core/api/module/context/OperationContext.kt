package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.core.api.view.ViewModel
import org.agorahq.agora.core.internal.module.context.DefaultOperationContext
import org.agorahq.agora.core.internal.module.context.DefaultPageRequestContext
import org.agorahq.agora.core.internal.module.context.DefaultResourceContext
import org.agorahq.agora.core.internal.module.context.DefaultViewContext
import org.agorahq.agora.core.internal.module.context.DefaultViewListingContext
import kotlin.jvm.JvmStatic

interface OperationContext {

    val site: SiteMetadata
    val user: User
    val converterService: ConverterService

    operator fun component1() = site

    operator fun component2() = user

    fun <P : Page> ResourceURL<P>.toPageRequestContext(): PageRequestContext<P> =
            DefaultPageRequestContext(
                    site = site,
                    user = user,
                    url = this,
                    converterService = converterService)

    fun <M : ViewModel> Result<out Resource, out Exception>.toViewContext(): ViewContext<M> =
            DefaultViewContext(
                    site = site,
                    user = user,
                    viewModel = converterService.convertToView<M>(get()).get(),
                    converterService = converterService)

    fun <M : ViewModel> Sequence<Resource>.toViewListingContext(): ViewListingContext<M> =
            DefaultViewListingContext(
                    site = site,
                    user = user,
                    items = map { converterService.convertToView<M>(it).get() },
                    converterService = converterService)

    companion object {

        @JvmStatic
        fun create(site: SiteMetadata, user: User, converterService: ConverterService) =
                DefaultOperationContext(
                        site = site,
                        user = user,
                        converterService = converterService)

    }
}