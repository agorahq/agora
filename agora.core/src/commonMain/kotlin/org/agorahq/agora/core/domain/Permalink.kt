package org.agorahq.agora.core.domain

interface Permalink<D : Document> {

    fun generatePermalink(): String

    fun matches(document: D): Boolean

    companion object
}