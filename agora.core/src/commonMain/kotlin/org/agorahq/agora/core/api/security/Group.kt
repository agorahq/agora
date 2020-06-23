package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.internal.security.DefaultGroup

/**
 * Represents a grouping of [User]s. Each [Group] must have an unique [name].
 */
interface Group {

    val name: String

    companion object {

        val ANONYMOUS: Group = DefaultGroup("Anonymous")

        fun create(name: String): Group = DefaultGroup(name)
    }
}
