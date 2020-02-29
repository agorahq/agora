package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageContent
import org.agorahq.agora.core.api.user.User
import org.agorahq.agora.core.internal.module.context.DefaultContentListingContext
import org.agorahq.agora.core.internal.module.context.DefaultOperationContext
import org.agorahq.agora.core.internal.module.context.DefaultPageContentListingContext
import org.agorahq.agora.core.internal.module.context.DefaultPageContext
import kotlin.jvm.JvmStatic

interface OperationContext {

    val site: SiteMetadata
    val user: User

    operator fun component1() = site

    operator fun component2() = user

    fun <P : Page> toPageContext(page: P): PageContext<P> =
            DefaultPageContext(
                    site = site,
                    user = user,
                    page = page)

    fun <P : Page> toPageListingContext(items: Sequence<P>): ContentListingContext<P> =
            DefaultContentListingContext(
                    site = site,
                    user = user,
                    items = items)

    fun <C : PageContent, P : Page> toPageContentListingContext(items: Sequence<C>, parent: P): PageContentListingContext<P, C> =
            DefaultPageContentListingContext(
                    site = site,
                    user = user,
                    items = items,
                    parent = parent)

    companion object {

        @JvmStatic
        fun create(site: SiteMetadata, user: User) =
                DefaultOperationContext(
                        site = site,
                        user = user)

    }
}