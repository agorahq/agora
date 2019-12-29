package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.Document
import org.agorahq.agora.core.domain.DomainObject
import org.agorahq.agora.core.domain.Permalink
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.facet.DocumentDetails

typealias AnyModule = Module<out DomainObject>

typealias AnyDocumentDetails = DocumentDetails<out Document, Permalink<out Document>>