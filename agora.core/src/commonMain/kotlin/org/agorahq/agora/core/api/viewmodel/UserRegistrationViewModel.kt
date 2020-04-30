package org.agorahq.agora.core.api.viewmodel

import org.agorahq.agora.core.api.data.FormField
import org.agorahq.agora.core.api.data.Validators
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.security.User
import org.agorahq.agora.core.api.view.ViewModel

data class UserRegistrationViewModel(
        val context: OperationContext,
        val email: FormField,
        val username: FormField = FormField.Empty(Validators.notBlank),
        val firstName: FormField = FormField.Empty(Validators.notBlank),
        val lastName: FormField = FormField.Empty(Validators.notBlank),
        override val owner: User = User.ANONYMOUS
) : ViewModel
