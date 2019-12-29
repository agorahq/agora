package org.agorahq.agora.core.module.facet

import org.agorahq.agora.core.module.Facet

interface DocumentListing : Facet {

    val path: String

    fun renderListing(): String

}