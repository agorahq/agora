package org.agorahq.agora.core.api.data

import org.agorahq.agora.core.api.data.FormField.Invalid
import org.agorahq.agora.core.api.data.FormField.Valid
import org.agorahq.agora.core.api.exception.FormFieldValidationFailedException

/**
 * Represents a field in a html form which has a [value]. Whenever validation is performed
 * it returns a new [FormField] with the corresponding state ([Empty], [Valid] or [Invalid]).
 */
sealed class FormField {

    abstract val value: String

    data class Dirty(
            override val value: String = ""
    ) : FormField()

    data class Valid(
            override val value: String
    ) : FormField()

    data class Invalid(
            override val value: String,
            val cause: String
    ) : FormField()

    /**
     * Validates the [FormField] and returns the corresponding new state:
     * - [Valid] if validation was successful or
     * - [Invalid] if not
     */
    fun validate(validator: (String) -> ValidationResult): FormField = when (val result = validator(value)) {
        is Result.Success -> Valid(value)
        is Result.Failure -> Invalid(value, result.exception.reason)
    }
}

typealias ValidationResult = Result<out Unit, out FormFieldValidationFailedException>
