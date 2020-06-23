package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Page
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.context.PageContext
import org.agorahq.agora.core.api.operation.context.PageURLContext
import org.agorahq.agora.core.api.operation.context.ResourceIdContext
import org.agorahq.agora.core.api.operation.context.ViewModelContext
import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.security.OperationDescriptor

typealias AnyOperation = Operation<out Resource, out OperationContext, out Any>

typealias AnyOperationDescriptor = OperationDescriptor<out Resource, out OperationContext, out Any>

typealias SaveResource<R, M> = Operation<R, ViewModelContext<M>, Unit>

typealias SaveResourceDescriptor<R, M> = OperationDescriptor<R, ViewModelContext<M>, Unit>

typealias DeleteResource<R> = Operation<R, ResourceIdContext, Unit>

typealias DeleteResourceDescriptor<R> = OperationDescriptor<R, ResourceIdContext, Unit>

typealias RenderPageList<R> = Operation<R, OperationContext, String>

typealias RenderPageListDescriptor<R> = OperationDescriptor<R, OperationContext, String>

typealias RenderResource<R> = Operation<R, PageURLContext<R>, String>

typealias RenderResourceDescriptor<R> = OperationDescriptor<R, PageURLContext<R>, String>

typealias RenderPageElementList<R, P> = Operation<R, PageContext<P>, String>

typealias RenderPageElementListDescriptor<R> = OperationDescriptor<R, PageContext<Page>, String>

typealias RenderPageElementForm<R, P> = Operation<R, PageContext<P>, String>

typealias RenderPageElementFormDescriptor<R> = OperationDescriptor<R, PageContext<Page>, String>
