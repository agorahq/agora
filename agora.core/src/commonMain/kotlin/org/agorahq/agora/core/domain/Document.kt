package org.agorahq.agora.core.domain

import org.hexworks.cobalt.Identifier

interface Document : DomainObject {

    override val id: Identifier
    override val createdAtMs: Long
    override val updatedAtMs: Long
    
    val markdownContent: String
    val permalink: String

    companion object
}