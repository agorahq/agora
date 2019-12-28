package org.agorahq.agora.core.domain

import org.hexworks.cobalt.Identifier

interface FeatureObject : DomainObject {

    val parentId: Identifier

    companion object
}