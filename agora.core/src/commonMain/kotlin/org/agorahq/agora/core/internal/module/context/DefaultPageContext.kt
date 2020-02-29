package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.ResourceURL
import org.agorahq.agora.core.api.module.context.PageContext
import org.agorahq.agora.core.api.security.User

class DefaultPageContext<P : Page>(
        override val site: SiteMetadata,
        override val user: User,
        override val url: ResourceURL<P>
) : PageContext<P>