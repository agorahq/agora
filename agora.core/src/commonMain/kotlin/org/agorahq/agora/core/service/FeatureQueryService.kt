package org.agorahq.agora.core.service

import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.domain.FeatureObject

interface FeatureQueryService<F : FeatureObject> : QueryService<F> {

    fun findByParent(parent: DomainObject): Sequence<F>

}