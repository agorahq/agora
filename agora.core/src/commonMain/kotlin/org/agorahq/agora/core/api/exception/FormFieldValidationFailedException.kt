package org.agorahq.agora.core.api.exception

class FormFieldValidationFailedException(val reason: String) : RuntimeException(reason) {
    override fun toString() = reason
}

