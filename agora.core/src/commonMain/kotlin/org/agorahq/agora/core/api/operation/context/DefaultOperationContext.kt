package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.security.User

data class DefaultOperationContext(
        override val site: SiteMetadata,
        override val user: User
) : OperationContext