package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.content.Page
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.context.PageContext
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.operation.context.ResourceContext
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor
import org.agorahq.agora.core.api.security.OperationType
import org.agorahq.agora.core.api.security.OperationType.PageElementFormRenderer
import org.agorahq.agora.core.api.security.OperationType.PageElementListRenderer
import org.agorahq.agora.core.api.security.OperationType.PageListRenderer
import org.agorahq.agora.core.api.security.OperationType.PageRenderer
import org.agorahq.agora.core.api.security.OperationType.ResourceDeleter
import org.agorahq.agora.core.api.security.OperationType.ResourceSaver

typealias AnyOperation = Operation<out Resource, out OperationContext, out OperationType<*, *, *>, out Any>

typealias SaveResource<R, M> = Operation<R, ViewModelContext<M>, ResourceSaver<R, M>, Unit>

typealias SaveResourceDescriptor<R, M> = OperationDescriptor<R, ViewModelContext<M>, Unit>

typealias DeleteResource<R> = Operation<R, ResourceIdContext, ResourceDeleter<R>, Unit>

typealias DeleteResourceDescriptor<R> = OperationDescriptor<R, ResourceIdContext, Unit>

typealias RenderPageList<R> = Operation<R, OperationContext, PageListRenderer<R>, String>

typealias RenderPageListDescriptor<R> = OperationDescriptor<R, OperationContext, String>

typealias RenderResource<R> = Operation<R, PageURLContext<R>, PageRenderer<R>, String>

typealias RenderResourceDescriptor<R> = OperationDescriptor<R, PageURLContext<R>, String>

typealias RenderPageElementList<R, P> = Operation<R, ResourceContext<P>, PageElementListRenderer<R, P>, String>

typealias RenderPageElementListDescriptor<R> = OperationDescriptor<R, ResourceContext<Page>, String>

typealias RenderPageElementForm<R, P> = Operation<R, PageContext<P>, PageElementFormRenderer<R, P>, String>

typealias RenderPageElementFormDescriptor<R> = OperationDescriptor<R, PageContext<Page>, String>