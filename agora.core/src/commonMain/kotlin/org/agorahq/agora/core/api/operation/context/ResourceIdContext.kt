package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.Message
import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.User
import org.hexworks.cobalt.core.api.UUID

data class ResourceIdContext(
        override val site: SiteMetadata,
        override val user: User,
        override val authorization: Authorization,
        override val message: Message? = null,
        val id: UUID
) : BaseOperationContext()
