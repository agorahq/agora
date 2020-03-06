package org.agorahq.agora.core.api.security

interface Authorization {

    val roles: Iterable<Role>
    val groups: Iterable<Group>

}