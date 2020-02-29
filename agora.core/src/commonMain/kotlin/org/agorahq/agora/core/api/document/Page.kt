package org.agorahq.agora.core.api.document

interface Page : Content {

    val url: PageURL<out Page>

    companion object
}