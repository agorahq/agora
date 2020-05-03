package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User

data class ResourceContext<R : Resource>(
        override val site: SiteMetadata,
        override val user: User,
        override val authorization: Authorization,
        override val message: Message? = null,
        val resource: R
) : OperationContext