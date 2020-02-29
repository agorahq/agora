package org.agorahq.agora.core.api.data

import org.agorahq.agora.core.api.services.ModuleRegistry

interface SiteMetadata {
    val title: String
    val email: String
    val description: String
    val baseUrl: String
    val moduleRegistry: ModuleRegistry
}