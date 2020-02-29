package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.ResourceURL
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.internal.module.context.DefaultOperationContext
import org.agorahq.agora.core.internal.module.context.DefaultPageContext
import org.agorahq.agora.core.internal.module.context.DefaultResourceContext
import org.agorahq.agora.core.internal.module.context.DefaultResourceListingContext
import kotlin.jvm.JvmStatic

interface OperationContext {

    val site: SiteMetadata
    val user: User

    operator fun component1() = site

    operator fun component2() = user

    fun <R : ContentResource> toResourceContext(resource: R): ResourceContext<R> =
            DefaultResourceContext(
                    site = site,
                    user = user,
                    resource = resource)

    fun <P : Page> toPageContext(url: ResourceURL<P>): PageContext<P> =
            DefaultPageContext(
                    site = site,
                    user = user,
                    url = url)

    fun <R : ContentResource> toListingContext(items: Sequence<R>): ResourceListingContext<R> =
            DefaultResourceListingContext(
                    site = site,
                    user = user,
                    items = items)

    companion object {

        @JvmStatic
        fun create(site: SiteMetadata, user: User) =
                DefaultOperationContext(
                        site = site,
                        user = user)

    }
}