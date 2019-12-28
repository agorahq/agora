package org.agorahq.agora.core.domain

interface Permalink<D : DomainObject> {

    fun generatePermalink(): String

    companion object
}