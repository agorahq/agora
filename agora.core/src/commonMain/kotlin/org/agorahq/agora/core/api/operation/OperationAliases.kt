package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.context.*

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

typealias RenderPageElementListDescriptor<R, P> = OperationDescriptor<R, PageContext<P>, String>

typealias RenderPageElementForm<R, P> = Operation<R, PageContext<P>, String>

typealias RenderPageElementFormDescriptor<R, P> = OperationDescriptor<R, PageContext<P>, String>
