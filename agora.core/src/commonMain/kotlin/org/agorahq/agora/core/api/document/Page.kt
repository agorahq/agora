package org.agorahq.agora.core.api.document

interface Page : ContentResource {

    val url: ResourceURL<out Page>

    companion object
}