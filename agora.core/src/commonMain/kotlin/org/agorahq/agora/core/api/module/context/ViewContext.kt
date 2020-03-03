package org.agorahq.agora.core.api.module.context

import org.agorahq.agora.core.api.view.ViewModel

interface ViewContext<out M : ViewModel> : OperationContext {

    val viewModel: M

    operator fun component3() = viewModel

    companion object

}