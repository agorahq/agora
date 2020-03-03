package org.agorahq.agora.core.api.exception

import org.agorahq.agora.core.api.view.ViewModel

class ViewConversionFailedException(
        viewModel: ViewModel, cause: Exception
) : RuntimeException("Can't convert view $viewModel to its corresponding resource", cause)