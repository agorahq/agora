package org.agorahq.agora.core.api.document

interface PageURL<P : Page> {

    fun generate(): String

    fun matches(page: P): Boolean

    companion object
}