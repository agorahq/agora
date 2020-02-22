package org.agorahq.agora.core.services

import org.agorahq.agora.core.domain.document.DocumentElement
import org.agorahq.agora.core.domain.document.DocumentPart

interface DocumentElementQueryService<E : DocumentElement> : QueryService<E> {

    fun findByParent(parent: DocumentPart): Sequence<E>

}