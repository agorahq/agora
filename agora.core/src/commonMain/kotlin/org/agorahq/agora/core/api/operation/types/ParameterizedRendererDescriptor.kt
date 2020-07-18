package org.agorahq.agora.core.api.operation.types

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.OperationDescriptor

interface ParameterizedRendererDescriptor<R : Resource, I : Any> : OperationDescriptor<R, I, String>
