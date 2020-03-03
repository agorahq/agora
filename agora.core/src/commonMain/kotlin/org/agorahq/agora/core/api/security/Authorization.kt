package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.resource.Resource

interface Authorization {

    fun <R : Resource, C : OperationContext, T : Any> Operation<R, C, T>.authorizeWith(
            authorization: Authorization
    ): Operation<R, C, T> {
        TODO()
    }

}