package org.agorahq.agora.core.api.operation.context

import org.agorahq.agora.core.api.data.SiteMetadata
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel

data class ViewModelContext<M : ViewModel>(
        override val site: SiteMetadata,
        override val user: User,
        val viewModel: M
) : OperationContext