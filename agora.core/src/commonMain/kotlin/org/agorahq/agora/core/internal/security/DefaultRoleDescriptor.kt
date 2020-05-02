package org.agorahq.agora.core.internal.security

import org.agorahq.agora.core.api.security.RoleDescriptor

data class DefaultRoleDescriptor(
        override val name: String
) : RoleDescriptor