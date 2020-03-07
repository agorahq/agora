package org.agorahq.agora.core.api.security

import org.agorahq.agora.core.internal.security.DefaultGroup

interface Group {

    val name: String

    companion object {

        val ANONYMOUS: Group = DefaultGroup("Anonymous")

        fun create(name: String): Group = DefaultGroup(name)
    }
}