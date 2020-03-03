package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.context.ViewListingContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ConverterService
import org.agorahq.agora.core.api.view.ViewModel

class DefaultViewListingContext<M : ViewModel>(
        override val site: SiteMetadata,
        override val user: User,
        override val items: Sequence<M>,
        override val converterService: ConverterService
) : ViewListingContext<M>