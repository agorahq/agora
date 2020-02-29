package org.agorahq.agora.core.api.extensions

import org.agorahq.agora.core.api.behavior.Secured
import org.agorahq.agora.core.api.module.Operation
import org.agorahq.agora.core.api.user.User

fun <O : Operation> User.canExecute(operation: O): Boolean = when (operation) {
    is Secured -> operation.canBeAccessedBy(this)
    else -> true
}