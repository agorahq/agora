package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.domain.document.DocumentElement
import org.agorahq.agora.core.domain.document.PageURL
import org.agorahq.agora.core.domain.document.DocumentPart
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.operations.DocumentDetailsRenderer
import org.agorahq.agora.core.module.operations.DocumentElementCreator

typealias AnyModule = Module<out DocumentPart>

typealias AnyDocumentDetails = DocumentDetailsRenderer<out Page, PageURL<out Page>>

typealias AnyDocumentElementCreating = DocumentElementCreator<DocumentElement>