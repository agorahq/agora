package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Resource

typealias AnyOperation = Operation<Resource, Any, Any>

typealias AnyOperationDescriptor = OperationDescriptor<out Resource, out Any, out Any>

typealias Procedure<R, I> = Operation<R, I, Unit>

typealias ProcedureDescriptor<R, I> = OperationDescriptor<R, I, Unit>

typealias Renderer<R> = Operation<R, Unit, String>

typealias RendererDescriptor<R> = OperationDescriptor<R, Unit, String>

typealias ParameterizedRenderer<R, I> = Operation<R, I, String>

typealias ParameterizedRendererDescriptor<R, I> = OperationDescriptor<R, I, String>

