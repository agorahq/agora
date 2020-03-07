package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel

data class ViewListingContext<M : ViewModel>(
        override val site: SiteMetadata,
        override val user: User,
        override val authorization: Authorization,
        val items: Sequence<M>
) : OperationContext