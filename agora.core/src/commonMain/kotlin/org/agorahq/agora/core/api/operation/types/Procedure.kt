package org.agorahq.agora.core.api.operation.types

import org.agorahq.agora.core.api.data.Resource
import org.agorahq.agora.core.api.operation.Operation

interface Procedure<R : Resource, I : Any> : Operation<R, I, Unit>
