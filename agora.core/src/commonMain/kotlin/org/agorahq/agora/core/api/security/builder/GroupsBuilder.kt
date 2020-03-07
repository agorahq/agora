package org.agorahq.agora.core.api.security.builder

import org.agorahq.agora.core.api.security.Group

class GroupsBuilder() {

    private val groups = mutableListOf<Group>()

    operator fun Group.unaryPlus() {
        groups.add(this)
    }

    fun build() = groups.toList()

}