package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.security.User

data class DefaultOperationContext(
        override val site: SiteMetadata,
        override val user: User
) : OperationContext