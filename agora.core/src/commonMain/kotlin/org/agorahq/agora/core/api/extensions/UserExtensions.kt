package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.behavior.Secured
import org.agorahq.agora.core.api.security.User

infix fun <O : AnyOperation> User.canExecute(operation: O): Boolean = when (operation) {
    is Secured -> operation.canBeAccessedBy(this)
    else -> true
}