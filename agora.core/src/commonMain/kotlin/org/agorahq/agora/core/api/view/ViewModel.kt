@file:Suppress("UNCHECKED_CAST")

package org.agorahq.agora.core.api.view

interface ViewModel {

    val isValid: Boolean
        get() = true

    fun validate(): ViewModel = this

}
