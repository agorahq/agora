package org.agorahq.agora.core.api.viewmodel

import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel

data class UserRegistrationViewModel(
        val email: String,
        val username: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val context: OperationContext
) : ViewModel