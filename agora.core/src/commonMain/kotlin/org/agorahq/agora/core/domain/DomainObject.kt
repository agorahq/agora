package org.agorahq.agora.core.domain

import org.hexworks.cobalt.Identifier

interface DomainObject {

    val id: Identifier
    val createdAtMs: Long
    val updatedAtMs: Long

    companion object
}