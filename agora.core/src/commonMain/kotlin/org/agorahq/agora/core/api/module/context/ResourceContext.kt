package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.resource.Resource

interface ResourceContext<R : Resource> : OperationContext {

    val resource: R

    operator fun component3() = resource

    companion object

}