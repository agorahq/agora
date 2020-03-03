package org.agorahq.agora.core.api.content

import org.agorahq.agora.core.api.resource.Resource

interface Page : Resource {

    val url: ResourceURL<out Page>

    companion object
}