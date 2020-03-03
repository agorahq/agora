package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.resource.Resource
import org.agorahq.agora.core.api.security.RoleDescriptor
import kotlin.reflect.KClass

class RoleBuilder() {

    fun inheritFrom(roleDescriptor: RoleDescriptor) {

    }

    operator fun <R : Resource> KClass<R>.invoke(fn: PermissionBuilder<R>.() -> Unit) {

    }
}