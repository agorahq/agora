package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.module.context.OperationContext
import org.agorahq.agora.core.api.module.context.ResourceContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.internal.module.context.DefaultResourceContext

fun <R : Resource> R.toResourceContext(context: OperationContext): ResourceContext<out R> =
        DefaultResourceContext(
                site = context.site,
                user = context.user,
                resource = this,
                converterService = context.converterService)

fun Result<out Resource, out Exception>.toResourceContext(context: OperationContext): Result<out ResourceContext<Resource>, out Exception> =
        map {
            DefaultResourceContext(
                    site = context.site,
                    user = context.user,
                    resource = it,
                    converterService = context.converterService)
        }