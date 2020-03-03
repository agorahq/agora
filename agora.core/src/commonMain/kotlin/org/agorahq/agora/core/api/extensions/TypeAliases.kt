package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.content.PageElement
import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.content.ResourceURL
import org.agorahq.agora.core.api.module.Module
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.module.context.ViewContext
import org.agorahq.agora.core.api.module.renderer.ChildResourceListRenderer
import org.agorahq.agora.core.api.module.renderer.FormRenderer
import org.agorahq.agora.core.api.module.renderer.PageRenderer
import org.agorahq.agora.core.api.module.renderer.ResourceListRenderer
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.resource.ResourceOperation
import org.agorahq.agora.core.api.view.ViewModel

typealias AnyModule = Module<Resource, ViewModel>

typealias AnyPageRenderer = PageRenderer<Page, ResourceURL<Page>>

typealias AnyResourceListRenderer = ResourceListRenderer<Resource, OperationContext>

typealias AnyChildResourceListRenderer = ChildResourceListRenderer<PageElement, ViewContext<ViewModel>>

typealias AnyContentFormRenderer = FormRenderer<Resource, out ViewModel>

typealias AnyOperation = Operation<out Resource, OperationContext, out Any>

typealias AnyResourceOperation = ResourceOperation<Resource>

typealias AnyContentOperation = Operation<out Resource, out OperationContext, out Any>
