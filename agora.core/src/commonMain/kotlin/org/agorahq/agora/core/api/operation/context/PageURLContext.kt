package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User

data class PageURLContext<P : Page>(
        override val site: SiteMetadata,
        override val user: User,
        override val authorization: Authorization,
        val url: ResourceURL<P>
) : OperationContext
