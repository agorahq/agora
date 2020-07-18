package org.agorahq.agora.core.api.operation.types

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation

interface ParameterizedRenderer<R : Resource, I : Any> : Operation<R, I, String>
