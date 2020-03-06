package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.security.Authorization
import org.agorahq.agora.core.api.security.Group
import org.agorahq.agora.core.api.security.Role

data class DefaultAuthorization(
        override val roles: Iterable<Role>,
        override val groups: Iterable<Group>
) : Authorization