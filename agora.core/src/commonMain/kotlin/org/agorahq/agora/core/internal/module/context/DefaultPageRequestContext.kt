package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.context.PageRequestContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ConverterService

class DefaultPageRequestContext<P : Page>(
        override val site: SiteMetadata,
        override val user: User,
        override val url: ResourceURL<P>,
        override val converterService: ConverterService
) : PageRequestContext<P>