package org.agorahq.agora.core.domain.document

interface Page : DocumentPart {

    val url: PageURL<out Page>

    companion object
}