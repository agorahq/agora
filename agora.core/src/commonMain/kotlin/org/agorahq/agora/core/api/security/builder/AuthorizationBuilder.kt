package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.security.RoleDescriptor

class AuthorizationBuilder() {

    operator fun RoleDescriptor.invoke(fn: RoleBuilder.() -> Unit) {

    }

}