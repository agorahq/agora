package org.agorahq.agora.core.api.operation

import org.agorahq.agora.core.api.data.Resource

typealias AnyOperation = Operation<Resource, Any, Any>

typealias AnyOperationDescriptor = OperationDescriptor<out Resource, out Any, out Any>


