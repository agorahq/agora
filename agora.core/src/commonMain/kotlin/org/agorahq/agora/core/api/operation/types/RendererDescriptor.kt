package org.agorahq.agora.core.api.operation.types

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.OperationDescriptor

interface RendererDescriptor<R : Resource> : OperationDescriptor<R, Unit, String>
