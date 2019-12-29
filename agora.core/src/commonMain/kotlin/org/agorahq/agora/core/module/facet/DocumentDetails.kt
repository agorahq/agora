package org.agorahq.agora.core.module.facet

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.Permalink
import org.agorahq.agora.core.module.Facet
import kotlin.reflect.KClass

interface DocumentDetails<D : Document, P : Permalink<D>> : Facet {

    val route: String

    val permalinkType: KClass<P>

    fun renderDocumentBy(permalink: P): String

}