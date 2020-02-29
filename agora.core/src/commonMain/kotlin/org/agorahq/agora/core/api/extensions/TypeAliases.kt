package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.document.Content
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageContent
import org.agorahq.agora.core.api.document.PageURL
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.operation.PageRenderer
import org.agorahq.agora.core.api.module.operation.PageContentCreator

typealias AnyModule = Module<Content>

typealias AnyPageRenderer = PageRenderer<Page, PageURL<Page>>

typealias AnyPageContentCreator = PageContentCreator<PageContent>
