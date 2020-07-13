@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.view

import org.agorahq.agora.core.api.operation.context.OperationContext

interface ViewModel {

    val isValid: Boolean
        get() = true

    val context: OperationContext

    fun validate(): ViewModel = this

}
