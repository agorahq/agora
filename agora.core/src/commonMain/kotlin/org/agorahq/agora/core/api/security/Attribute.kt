package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.api.resource.Resource

interface Attribute<R : Resource> {

    fun User.canAccess(resource: R)
}