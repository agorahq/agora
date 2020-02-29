package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.module.context.PageContext
import org.agorahq.agora.core.api.user.User

class DefaultPageContext<P : Page>(
        override val site: SiteMetadata,
        override val user: User,
        override val page: P
) : PageContext<P>