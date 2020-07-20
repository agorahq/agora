package org.agorahq.agora.core.api.data

import org.agorahq.agora.core.api.service.OperationRegistry

interface SiteMetadata {
    val title: String
    val email: String
    val description: String
    val baseUrl: String
    val operationRegistry: OperationRegistry
}
