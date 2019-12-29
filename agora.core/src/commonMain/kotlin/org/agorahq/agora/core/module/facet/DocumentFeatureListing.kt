package org.agorahq.agora.core.module.facet

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.module.Facet

interface DocumentFeatureListing : Facet {

    fun renderListingFor(document: Document): String

}