package org.agorahq.agora.core.api.security

/**
 * Describes a [Permission]. Each [PermissionDescriptor] must have
 * an unique [name].
 */
interface PermissionDescriptor {

    val name: String

    operator fun component1() = name
}
