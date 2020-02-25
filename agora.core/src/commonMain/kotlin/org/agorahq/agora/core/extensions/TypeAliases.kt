package org.agorahq.agora.core.extensions

import org.agorahq.agora.core.domain.document.Content
import org.agorahq.agora.core.domain.document.Page
import org.agorahq.agora.core.domain.document.PageContent
import org.agorahq.agora.core.domain.document.PageURL
import org.agorahq.agora.core.module.Module
import org.agorahq.agora.core.module.operations.PageRenderer
import org.agorahq.agora.core.module.operations.PageContentCreator

typealias AnyModule = Module<out Content>

typealias AnyPageRenderer = PageRenderer<out Page, PageURL<out Page>>

typealias AnyPageContentCreator = PageContentCreator<PageContent>