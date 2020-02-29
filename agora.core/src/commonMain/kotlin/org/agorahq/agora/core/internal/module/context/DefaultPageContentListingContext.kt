package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.context.PageContentListingContext
import org.agorahq.agora.core.api.user.User

class DefaultPageContentListingContext<P : Page, C : Content>(
        override val site: SiteMetadata,
        override val user: User,
        override val items: Sequence<C>,
        override val parent: P
) : PageContentListingContext<P, C>