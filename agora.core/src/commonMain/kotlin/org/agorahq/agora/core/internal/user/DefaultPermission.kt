package org.agorahq.agora.core.internal.user

import org.agorahq.agora.core.api.security.Permission

data class DefaultPermission(
        override val operation: String,
        override val subject: String
) : Permission