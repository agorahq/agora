package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.view.ViewModel

class ViewConversionFailedException(
        viewModel: ViewModel,
        cause: Exception,
        private val msg: String = "Can't convert view $viewModel to its corresponding resource"
) : RuntimeException(msg, cause) {
    override fun toString() = msg
}
