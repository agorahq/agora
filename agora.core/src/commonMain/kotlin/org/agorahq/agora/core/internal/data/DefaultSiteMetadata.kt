package org.agorahq.agora.core.internal.data

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.services.ModuleRegistry

class DefaultSiteMetadata(
        override val title: String,
        override val email: String,
        override val description: String,
        override val baseUrl: String,
        override val moduleRegistry: ModuleRegistry
) : SiteMetadata