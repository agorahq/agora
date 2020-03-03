package org.agorahq.agora.core.internal.module.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ConverterService

data class DefaultOperationContext(
        override val site: SiteMetadata,
        override val user: User,
        override val converterService: ConverterService
) : OperationContext