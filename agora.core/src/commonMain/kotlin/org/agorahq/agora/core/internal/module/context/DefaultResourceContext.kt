package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ConverterService

class DefaultResourceContext<R : Resource>(
        override val site: SiteMetadata,
        override val user: User,
        override val resource: R,
        override val converterService: ConverterService
) : ResourceContext<R>