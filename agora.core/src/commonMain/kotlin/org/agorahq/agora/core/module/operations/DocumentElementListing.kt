package org.agorahq.agora.core.module.operations

import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.module.Operation

interface DocumentElementListing : Operation {

    fun renderListingFor(page: Page): String

}