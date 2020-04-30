package org.agorahq.agora.core.api.data

import org.agorahq.agora.core.api.data.FormField.Empty
import org.agorahq.agora.core.api.data.FormField.Invalid
import org.agorahq.agora.core.api.data.FormField.Valid
import org.agorahq.agora.core.api.exception.FormFieldValidationFailedException

/**
 * Represents a field in a html form which has a [value] and
 * a corresponding [validator]. Whenever validation is
 * performed it returns a new [FormField] with the corresponding
 * state ([Empty], [Valid] or [Invalid]).
 */
sealed class FormField {

    abstract val value: String
    abstract val validator: (String) -> ValidationResult

    data class Empty(
            override val validator: (String) -> ValidationResult
    ) : FormField() {
        override val value = ""
    }

    data class Valid(
            override val value: String,
            override val validator: (String) -> ValidationResult
    ) : FormField()

    data class Invalid(
            override val value: String,
            override val validator: (String) -> ValidationResult,
            val cause: String
    ) : FormField()

    /**
     * Validates the [FormField] and returns the corresponding new state:
     * - [Valid] if validation was successful or
     * - [Invalid] if not
     */
    fun validate(): FormField = when (val result = validator(value)) {
        is Result.Success -> Valid(value, validator)
        is Result.Failure -> Invalid(value, validator, result.exception.reason)
    }
}

typealias ValidationResult = Result<out Unit, out FormFieldValidationFailedException>
