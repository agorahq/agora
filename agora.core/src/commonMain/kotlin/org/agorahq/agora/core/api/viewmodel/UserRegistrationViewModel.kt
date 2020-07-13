package org.agorahq.agora.core.api.viewmodel

import org.agorahq.agora.core.api.data.FormField
import org.agorahq.agora.core.api.data.Validators
import org.agorahq.agora.core.api.operation.context.OperationContext
import org.agorahq.agora.core.api.view.ViewModel

data class UserRegistrationViewModel(
        override val context: OperationContext,
        val email: String,
        val firstName: String,
        val lastName: String,
        val username: FormField = FormField.Dirty()
) : ViewModel {

    override val isValid: Boolean
        get() = username is FormField.Valid

    override fun validate(): UserRegistrationViewModel {
        return this.copy(
                username = username.validate(Validators.notBlank))
    }

    companion object
}
