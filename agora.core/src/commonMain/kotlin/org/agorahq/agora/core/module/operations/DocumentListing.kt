package org.agorahq.agora.core.module.operations

import org.agorahq.agora.core.module.Operation

interface DocumentListing : Operation {

    val route: String

    fun renderListing(): String

}