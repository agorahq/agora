package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.module.context.ResourceListingContext
import org.agorahq.agora.core.api.security.User

class DefaultResourceListingContext<R : ContentResource>(
        override val site: SiteMetadata,
        override val user: User,
        override val items: Sequence<R>
) : ResourceListingContext<R>