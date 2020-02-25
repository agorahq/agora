package org.agorahq.agora.core.domain.document

interface Page : Content {

    val url: PageURL<out Page>

    companion object
}