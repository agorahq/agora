package org.agorahq.agora.core.internal.data

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.service.OperationRegistry

class DefaultSiteMetadata(
        override val title: String,
        override val email: String,
        override val description: String,
        override val baseUrl: String,
        override val operationRegistry: OperationRegistry
) : SiteMetadata
