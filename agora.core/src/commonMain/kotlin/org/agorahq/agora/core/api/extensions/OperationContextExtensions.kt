package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.data.Result
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.operation.context.ResourceContext
import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.view.ViewModel

//fun <R : Resource> R.toResourceContext(context: OperationContext): ResourceContext<out R> =
//        DefaultResourceContext(
//                site = context.site,
//                user = context.user,
//                resource = this,
//                converterService = context.converterService)
//
//fun <M : ViewModel> OperationContext.toViewContext(viewModel: M): ViewContext<M> =
//        DefaultViewContext(
//                site = site,
//                user = user,
//                viewModel = viewModel,
//                converterService = converterService)
//
//fun Result<out Resource, out Exception>.toResourceContext(context: OperationContext): Result<out ResourceContext<Resource>, out Exception> =
//        map {
//            DefaultResourceContext(
//                    site = context.site,
//                    user = context.user,
//                    resource = it,
//                    converterService = context.converterService)
//        }