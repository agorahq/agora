package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.document.ContentResource
import org.agorahq.agora.core.api.document.Page
import org.agorahq.agora.core.api.document.PageContentResource
import org.agorahq.agora.core.api.document.ResourceURL
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.renderer.ContentFormRenderer
import org.agorahq.agora.core.api.module.renderer.ContentResourceListRenderer
import org.agorahq.agora.core.api.module.renderer.PageContentResourceListRenderer
import org.agorahq.agora.core.api.module.renderer.PageRenderer
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.resource.ResourceOperation

typealias AnyModule = Module<ContentResource>

typealias AnyPageRenderer = PageRenderer<Page, ResourceURL<Page>>

typealias AnyResourceListRenderer = ContentResourceListRenderer<ContentResource, OperationContext>

typealias AnyPageContentListRenderer = PageContentResourceListRenderer<PageContentResource, OperationContext>

typealias AnyContentFormRenderer = ContentFormRenderer<out ContentResource>

typealias AnyOperation = Operation<out Resource, out OperationContext, out Any>

typealias AnyContentResourceOperation = ResourceOperation<ContentResource>

typealias AnyContentOperation = Operation<out ContentResource, out OperationContext, out Any>
