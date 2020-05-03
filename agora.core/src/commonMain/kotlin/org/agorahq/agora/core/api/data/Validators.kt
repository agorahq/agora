package org.agorahq.agora.core.api.data

import org.agorahq.agora.core.api.exception.FormFieldValidationFailedException

object Validators {

    private val EMAIL_REGEX = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()

    val email: (String) -> ValidationResult = { value: String ->
        if (value.matches(EMAIL_REGEX)) {
            Result.Success(Unit)
        } else Result.Failure(FormFieldValidationFailedException(
                "$value is not a valid e-mail address."
        ))
    }

    val notBlank: (String) -> ValidationResult = { value: String ->
        if (value.isNotBlank()) {
            Result.Success(Unit)
        } else Result.Failure(FormFieldValidationFailedException(
                "$value cannot be blank."
        ))
    }

}
