package org.agorahq.agora.core.api.view

import org.agorahq.agora.core.api.security.User

interface ViewModel {

    val owner: User
}