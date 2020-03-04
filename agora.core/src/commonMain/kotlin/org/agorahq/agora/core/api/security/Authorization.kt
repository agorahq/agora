package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.operation.Operation
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource

interface Authorization {

    fun <R : Resource, C : OperationContext, T : OperationType<R, C, U>, U : Any> Operation<R, C, T, U>.authorizeWith(
            authorization: Authorization
    ): Operation<R, C, T, U> {
        TODO()
    }

}