package org.agorahq.agora.core.domain

import org.agorahq.agora.core.services.ModuleRegistry

/**
 * [Site] contains metadata about the whole site.
 * // TODO: make this more abstract.
 */
class Site(
        val title: String,
        val email: String,
        val description: String,
        val host: String,
        val port: Int,
        val baseUrl: String,
        val moduleRegistry: ModuleRegistry
)