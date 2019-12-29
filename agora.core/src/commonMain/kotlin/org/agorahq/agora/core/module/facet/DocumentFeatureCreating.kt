package org.agorahq.agora.core.module.facet

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.FeatureObject
import org.agorahq.agora.core.module.Facet
import kotlin.reflect.KClass

interface DocumentFeatureCreating<F : FeatureObject> : Facet {

    val creates: KClass<F>
    val route: String

    fun renderFormFor(parent: Document): String

    fun store(featureObject: F)

}