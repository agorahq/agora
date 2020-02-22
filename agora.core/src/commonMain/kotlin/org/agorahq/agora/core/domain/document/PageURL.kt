package org.agorahq.agora.core.domain.document

interface PageURL<D : Page> {

    fun generate(): String

    fun matches(document: D): Boolean

    companion object
}